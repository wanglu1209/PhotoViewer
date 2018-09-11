package com.wanglu.photoviewerlibrary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_picture.*

class PhotoViewerFragment : BaseLazyFragment() {


    var exitListener: OnExitListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.item_picture, container, false)
    }

    interface OnExitListener {
        fun exit()
    }

    override fun onLazyLoad() {

        val mExitLocation: IntArray = arguments!!.getIntArray("exit_location")
        val mImgSize: IntArray = arguments!!.getIntArray("img_size")
        val mPicData = arguments!!.getString("pic_data")

        PhotoViewer.mInterface!!.show(mIv, mPicData)
        var alpha = 1f  // 透明度
        mIv.setExitLocation(mExitLocation)
        mIv.setImgSize(mImgSize)

        var intAlpha = 255
        root.background.alpha = intAlpha
        mIv.rootView = root
        mIv.setOnViewFingerUpListener {
            alpha = 1f
            intAlpha = 255
        }

        // 注册退出Activity 滑动大于一定距离后退出
        mIv.setExitListener {
            if (exitListener != null) {
                exitListener!!.exit()
            }
        }


        mIv.setOnViewDragListener { dx, dy ->

            mIv.scrollBy((-dx).toInt(), (-dy).toInt())  // 移动图像
            alpha -= dy * 0.001f
            intAlpha -= (dy * 0.5).toInt()
            if (alpha > 1) alpha = 1f
            else if (alpha < 0) alpha = 0f
            if (intAlpha < 0) intAlpha = 0
            else if (intAlpha > 255) intAlpha = 255
            root.background.alpha = intAlpha    // 更改透明度

            if (alpha >= 0.6)
                mIv.attacher.scale = alpha   // 更改大小


        }


        mIv.setOnClickListener {

            mIv.exit()
        }

    }

}