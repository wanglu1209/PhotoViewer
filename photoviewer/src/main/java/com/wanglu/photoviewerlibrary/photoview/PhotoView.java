/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.wanglu.photoviewerlibrary.photoview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Scroller;

import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * A zoomable ImageView. See {@link PhotoViewAttacher} for most of the details on how the zooming
 * is accomplished
 */
public class PhotoView extends AppCompatImageView {

    private PhotoViewAttacher attacher;
    private ScaleType pendingScaleType;
    private Scroller mScroller;
    private OnViewFingerUpL l;

    private OnExitListener mExitListener;

    private View mRootView;


    private int[] mImgSize; // 图片大小


    private int[] mExitLocation;


    float alpha = 1f;
    int intAlpha = 255;


    public void setExitLocation(int[] exitLocation) {
        mExitLocation = exitLocation;
    }

    public void setExitListener(OnExitListener exitListener) {
        mExitListener = exitListener;
    }


    public PhotoView(Context context) {
        this(context, null);
    }

    public PhotoView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public PhotoView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        mScroller = new Scroller(context);
        init();
    }

    private void init() {
        attacher = new PhotoViewAttacher(this);


        //We always pose as a Matrix scale type, though we can change to another scale type
        //via the attacher
        super.setScaleType(ScaleType.MATRIX);
        //apply the previously applied scale type
        if (pendingScaleType != null) {
            setScaleType(pendingScaleType);
            pendingScaleType = null;
        }
        attacher.setOnViewFingerUpListener(new OnViewFingerUpListener() {
            @Override
            public void onViewFingerUp() {
                alpha = 1f;
                intAlpha = 255;
                // 这里恢复位置和透明度
                if (getRootView().getBackground().getAlpha() == 0 && mExitListener != null) {
                    exit();
                } else {
                    ValueAnimator va = ValueAnimator.ofFloat(PhotoView.this.getAlpha(), 1f);
                    ValueAnimator bgVa = ValueAnimator.ofInt(getRootView().getBackground().getAlpha(), 255);
                    va.setDuration(200);
                    bgVa.setDuration(200);
                    va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            PhotoView.this.setAlpha((Float) animation.getAnimatedValue());
                        }
                    });
                    va.start();

                    bgVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            getRootView().getBackground().setAlpha((Integer) animation.getAnimatedValue());
                        }
                    });
                    bgVa.start();

                    mScroller.startScroll(
                            getScrollX(),
                            getScrollY(),
                            -getScrollX(),
                            -getScrollY(), 200
                    );


                    postInvalidate();
                    if (l != null) {
                        l.up();
                    }
                }
            }

        });
    }


    public void exit() {

        Matrix m = new Matrix();

        m.postScale(((float) mImgSize[0] / getWidth()), ((float) mImgSize[1] / getHeight()));

        ObjectAnimator scaleOa = ObjectAnimator.ofFloat(this, "scale", attacher.getScale(m));

        ObjectAnimator xOa = ObjectAnimator.ofFloat(this, "translationX", mExitLocation[0] - getWidth() / 2 + getScrollX());
        ObjectAnimator yOa = ObjectAnimator.ofFloat(this, "translationY", mExitLocation[1] - getHeight() / 2 + getScrollY());

        AnimatorSet set = new AnimatorSet();
        set.setDuration(250);
        set.playTogether(scaleOa, xOa, yOa);


        if (getRootView().getBackground().getAlpha() > 0) {
            ValueAnimator bgVa = ValueAnimator.ofInt(getRootView().getBackground().getAlpha(), 0);
            bgVa.setDuration(250);
            bgVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    getRootView().getBackground().setAlpha((Integer) animation.getAnimatedValue());
                }
            });
            bgVa.start();
        }
        set.start();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mExitListener.exit();
            }
        }, 270);
    }

    public void setRootView(View rootView) {
        mRootView = rootView;
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    public void setImgSize(int[] imgSize) {
        mImgSize = imgSize;
    }

    /**
     * Get the current {@link PhotoViewAttacher} for this view. Be wary of holding on to references
     * to this attacher, as it has a reference to this view, which, if a reference is held in the
     * wrong place, can cause memory leaks.
     *
     * @return the attacher.
     */
    public PhotoViewAttacher getAttacher() {
        return attacher;
    }

    @Override
    public ScaleType getScaleType() {
        return attacher.getScaleType();
    }

    @Override
    public Matrix getImageMatrix() {
        return attacher.getImageMatrix();
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        attacher.setOnLongClickListener(l);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        attacher.setOnClickListener(l);
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (attacher == null) {
            pendingScaleType = scaleType;
        } else {
            attacher.setScaleType(scaleType);
        }
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        // setImageBitmap calls through to this method
        if (attacher != null) {
            attacher.update();
        }
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        if (attacher != null) {
            attacher.update();
        }
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        if (attacher != null) {
            attacher.update();
        }
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        boolean changed = super.setFrame(l, t, r, b);
        if (changed) {
            attacher.update();
        }
        return changed;
    }

    public void setRotationTo(float rotationDegree) {
        attacher.setRotationTo(rotationDegree);
    }

    public void setRotationBy(float rotationDegree) {
        attacher.setRotationBy(rotationDegree);
    }

    @Deprecated
    public boolean isZoomEnabled() {
        return attacher.isZoomEnabled();
    }

    public boolean isZoomable() {
        return attacher.isZoomable();
    }

    public void setZoomable(boolean zoomable) {
        attacher.setZoomable(zoomable);
    }

    public RectF getDisplayRect() {
        return attacher.getDisplayRect();
    }

    public void getDisplayMatrix(Matrix matrix) {
        attacher.getDisplayMatrix(matrix);
    }

    public boolean setDisplayMatrix(Matrix finalRectangle) {
        return attacher.setDisplayMatrix(finalRectangle);
    }

    public void getSuppMatrix(Matrix matrix) {
        attacher.getSuppMatrix(matrix);
    }

    public boolean setSuppMatrix(Matrix matrix) {
        return attacher.setDisplayMatrix(matrix);
    }

    public float getMinimumScale() {
        return attacher.getMinimumScale();
    }

    public float getMediumScale() {
        return attacher.getMediumScale();
    }

    public float getMaximumScale() {
        return attacher.getMaximumScale();
    }

    public float getScale() {
        return attacher.getScale();
    }

    public void setAllowParentInterceptOnEdge(boolean allow) {
        attacher.setAllowParentInterceptOnEdge(allow);
    }

    public void setMinimumScale(float minimumScale) {
        attacher.setMinimumScale(minimumScale);
    }

    public void setMediumScale(float mediumScale) {
        attacher.setMediumScale(mediumScale);
    }

    public void setMaximumScale(float maximumScale) {
        attacher.setMaximumScale(maximumScale);
    }

    public void setScaleLevels(float minimumScale, float mediumScale, float maximumScale) {
        attacher.setScaleLevels(minimumScale, mediumScale, maximumScale);
    }

    public void setOnMatrixChangeListener(OnMatrixChangedListener listener) {
        attacher.setOnMatrixChangeListener(listener);
    }

    public void setOnPhotoTapListener(OnPhotoTapListener listener) {
        attacher.setOnPhotoTapListener(listener);
    }

    public void setOnOutsidePhotoTapListener(OnOutsidePhotoTapListener listener) {
        attacher.setOnOutsidePhotoTapListener(listener);
    }

    public void setOnViewTapListener(OnViewTapListener listener) {
        attacher.setOnViewTapListener(listener);
    }

    public void setOnViewDragListener(OnViewDragListener listener) {
        attacher.setOnViewDragListener(listener);
    }

    public interface OnViewFingerUpL {
        void up();
    }

    public interface OnExitListener {
        void exit();
    }

    public void setOnViewFingerUpListener(OnViewFingerUpL listener) {
        l = listener;

    }

    public void setScale(float scale) {
        attacher.setScale(scale);
    }

    public void setScale(float scale, boolean animate) {
        attacher.setScale(scale, animate);
    }

    public void setScale(float scale, float focalX, float focalY, boolean animate) {
        attacher.setScale(scale, focalX, focalY, animate);
    }

    public void setZoomTransitionDuration(int milliseconds) {
        attacher.setZoomTransitionDuration(milliseconds);
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        attacher.setOnDoubleTapListener(onDoubleTapListener);
    }

    public void setOnScaleChangeListener(OnScaleChangedListener onScaleChangedListener) {
        attacher.setOnScaleChangeListener(onScaleChangedListener);
    }

    public void setOnSingleFlingListener(OnSingleFlingListener onSingleFlingListener) {
        attacher.setOnSingleFlingListener(onSingleFlingListener);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
