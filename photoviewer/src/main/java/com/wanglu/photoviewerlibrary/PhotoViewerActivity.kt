package com.wanglu.photoviewerlibrary

import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import com.wanglu.photoviewerlibrary.PhotoViewer.PIC_DATA
import com.wanglu.photoviewerlibrary.PhotoViewer.mInterface
import com.wanglu.photoviewerlibrary.photoview.PhotoView
import kotlinx.android.synthetic.main.activity_photoviewer.*


/**
 * Created by WangLu on 2018/7/15.
 */
class PhotoViewerActivity : AppCompatActivity() {

    var mPicData: List<String>? = null
    var mWidth: Int = 0
    var mHeight: Int = 0
    var mCountRow: Int = 0
    var mLeftSpace: Int = 0
    var mTopSpace: Int = 0
    var mCurrentPage: Int = 0
    var mTotalPage: Int = 0
    var mClickViewLocation: IntArray? = null
    var mClickLocation = IntArray(2)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photoviewer)

        mPicData = intent.getStringArrayListExtra(PIC_DATA)
        mWidth = dp2px(intent.getIntExtra(PhotoViewer.WIDTH, -1))
        mHeight = dp2px(intent.getIntExtra(PhotoViewer.HEIGHT, -1))
        mCurrentPage = intent.getIntExtra(PhotoViewer.CURRENT_PAGE, 0)
        mTotalPage = intent.getIntExtra(PhotoViewer.TOTAL_PAGE, 0)

        mCountRow = intent.getIntExtra(PhotoViewer.COUNT_ROW, 3)
        mLeftSpace = intent.getIntExtra(PhotoViewer.LEFT_SPACE, dp2px(5))
        mTopSpace = intent.getIntExtra(PhotoViewer.TOP_SPACE, dp2px(5))
        mClickViewLocation = intent.getIntArrayExtra(PhotoViewer.CLICK_VIEW_LOCATION)
        mClickViewLocation!![0] += mWidth / 2
        mClickViewLocation!![1] += mHeight / 2

        mClickLocation[0] = mClickViewLocation!![0]
        mClickLocation[1] = mClickViewLocation!![1]

        // 计算出左边第一个图片的位置
        mClickViewLocation!![0] -= mCurrentPage % mCountRow * mWidth + mCurrentPage % mCountRow * mLeftSpace
        // 计算出上面第一个图片的位置
        mClickViewLocation!![1] -= mCurrentPage / mCountRow * mHeight + mCurrentPage / mCountRow * mTopSpace


        if (mClickViewLocation == null) {
            throw RuntimeException("Location is required!")
        }


        val adapter = PhotoViewerPagerAdapter()
        mLookPicVP.adapter = adapter
        mLookPicVP.currentItem = mCurrentPage


        mLookPicVP.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {

                mClickLocation[0] = (position % mCountRow) * mWidth + position * mLeftSpace + mClickViewLocation!![0]
                mClickLocation[1] = position / 3 * mHeight + position / 3 * mTopSpace + mClickViewLocation!![1]

            }

        })

    }

    inner class PhotoViewerPagerAdapter : PagerAdapter() {
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return mPicData!!.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = layoutInflater.inflate(R.layout.item_picture, null)
            val iv = view.findViewById<PhotoView>(R.id.mIv)
            mInterface!!.show(iv, mPicData!![position])
            var alpha = 1f  // 透明度
            iv.setExitLocation(mClickLocation)

            iv.setOnViewFingerUpListener {
                alpha = 1f
            }

            // 注册退出Activity 滑动大于一定距离后退出
            iv.setExitListener { exit() }


            iv.setOnViewDragListener { dx, dy ->

                iv.scrollBy(-dx.toInt(), -dy.toInt())   // 移动图像
                alpha -= dy * 0.001f
                if (alpha > 1) alpha = 1f
                else if (alpha < 0) alpha = 0f
                iv.alpha = alpha    // 更改透明度
                iv.attacher.scale = alpha   // 更改大小
            }


            container.addView(view)
            iv.setOnClickListener { exit() }

            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

    }

    fun exit() {
        finish()
    }


    fun dp2px(dipValue: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dipValue.toFloat(), this.resources.displayMetrics).toInt()
    }


}