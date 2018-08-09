package com.wanglu.photoviewerlibrary

import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.wanglu.photoviewerlibrary.PhotoViewer.PIC_DATA
import com.wanglu.photoviewerlibrary.PhotoViewer.mInterface
import com.wanglu.photoviewerlibrary.Utils.dp2px
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

    /**
     * 小圆点的drawable
     * 下标0的为没有被选中的
     * 下标1的为已经被选中的
     */
    private val mDot = intArrayOf(R.drawable.no_selected_dot, R.drawable.selected_dot)
    /**
     * 存放小圆点的Group
     */
    private var mDotGroup: LinearLayout? = null
    /**
     * 存放没有被选中的小圆点Group和已经被选中小圆点
     */
    private var mFrameLayout: FrameLayout? = null
    /**
     * 选中的小圆点
     */
    private var mSelectedDot: View? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photoviewer)

        mPicData = intent.getStringArrayListExtra(PIC_DATA)
        mWidth = intent.getIntExtra(PhotoViewer.WIDTH, -1)
        mHeight = intent.getIntExtra(PhotoViewer.HEIGHT, -1)
        mCurrentPage = intent.getIntExtra(PhotoViewer.CURRENT_PAGE, 0)
        mTotalPage = intent.getIntExtra(PhotoViewer.TOTAL_PAGE, 0)

        mCountRow = intent.getIntExtra(PhotoViewer.COUNT_ROW, 3)
        mLeftSpace = dp2px(this, intent.getIntExtra(PhotoViewer.LEFT_SPACE, 5))
        mTopSpace = dp2px(this, intent.getIntExtra(PhotoViewer.TOP_SPACE, 5))
        mClickViewLocation = intent.getIntArrayExtra(PhotoViewer.CLICK_VIEW)
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

        var p = 0

        mLookPicVP.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                if (mSelectedDot != null && mPicData!!.size > 1) {
                    val dx = mDotGroup!!.getChildAt(1).x - mDotGroup!!.getChildAt(0).x
                    if (position != mCurrentPage) {
                        p += position - mCurrentPage
                        mCurrentPage = position
                    }
                    mSelectedDot!!.translationX = p * dx + positionOffset * dx
                }
            }

            override fun onPageSelected(position: Int) {

                mClickLocation[0] = (position % mCountRow) * mWidth + (position % mCountRow) * mLeftSpace + mClickViewLocation!![0]
                mClickLocation[1] = position / mCountRow * mHeight + position / mCountRow * mTopSpace + mClickViewLocation!![1]


            }

        })








        if (mPicData!!.size > 1)
            root.post {

                /**
                 * 实例化两个Group
                 */
                mFrameLayout = FrameLayout(this)
                mDotGroup = LinearLayout(this)

                if (mDotGroup!!.childCount != 0)
                    mDotGroup!!.removeAllViews()
                val dotParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                /**
                 * 未选中小圆点的间距
                 */
                dotParams.rightMargin = dp2px(this, 12)

                /**
                 * 创建未选中的小圆点
                 */
                for (i in 0 until mPicData!!.size) {
                    val iv = ImageView(this)
                    iv.setImageDrawable(resources.getDrawable(mDot[0]))
                    iv.layoutParams = dotParams
                    mDotGroup!!.addView(iv)
                }

                /**
                 * 设置小圆点Group的方向为水平
                 */
                mDotGroup!!.orientation = LinearLayout.HORIZONTAL
                /**
                 * 设置小圆点在中间
                 */
                mDotGroup!!.gravity = Gravity.CENTER or Gravity.BOTTOM
                /**
                 * 两个Group的大小都为match_parent
                 */
                val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)


                params.bottomMargin = dp2px(this, 20)
                /**
                 * 首先添加小圆点的Group
                 */
                root.addView(mDotGroup, params)

                mDotGroup!!.post {

                    if (mSelectedDot == null) {
                        val iv = ImageView(this)
                        iv.setImageDrawable(resources.getDrawable(mDot[1]))
                        val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        /**
                         * 设置选中小圆点的左边距
                         */
                        params.leftMargin = mDotGroup!!.getChildAt(0).x.toInt() + dotParams.rightMargin * mCurrentPage + mDotGroup!!.getChildAt(0).width * mCurrentPage
                        params.gravity = Gravity.BOTTOM
                        mFrameLayout!!.addView(iv, params)
                        mSelectedDot = iv
                    }
                    /**
                     * 然后添加包含未选中圆点和选中圆点的Group
                     */
                    root.addView(mFrameLayout, params)
                }
            }

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
            iv.setImgSize(intArrayOf(mWidth, mHeight))

            var intAlpha = 255
            root.background.alpha = intAlpha
            iv.rootView = root
            iv.setOnViewFingerUpListener {
                alpha = 1f
                intAlpha = 255
            }

            // 注册退出Activity 滑动大于一定距离后退出
            iv.setExitListener { exit() }


            iv.setOnViewDragListener { dx, dy ->

                (iv.parent as View).scrollBy((-dx).toInt(), (-dy).toInt())  // 移动图像
                alpha -= dy * 0.001f
                intAlpha -= dy.toInt()
                if (alpha > 1) alpha = 1f
                else if (alpha < 0) alpha = 0f
                if (intAlpha < 0) intAlpha = 0
                else if (intAlpha > 255) intAlpha = 255
                root.background.alpha = intAlpha    // 更改透明度
                if (alpha >= 0.6)
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


    override fun onPause() {
        overridePendingTransition(0, 0)
        super.onPause()
    }


}