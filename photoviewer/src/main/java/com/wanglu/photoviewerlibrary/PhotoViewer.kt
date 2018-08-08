package com.wanglu.photoviewerlibrary

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView


/**
 * Created by WangLu on 2018/7/15.
 */
object PhotoViewer {
    internal const val PIC_DATA = "PIC_DATA"
    internal const val WIDTH = "WIDTH"
    internal const val HEIGHT = "HEIGHT"
    internal const val CURRENT_PAGE = "CURRENT_PAGE"
    internal const val TOTAL_PAGE = "TOTAL_PAGE"
    internal const val LEFT_SPACE = "LEFT_SPACE"
    internal const val TOP_SPACE = "TOP_SPACE"
    internal const val COUNT_ROW = "COUNT_ROW"
    internal const val CLICK_VIEW = "CLICK_VIEW"
    internal var mInterface: ShowImageViewInterface? = null

    private val i = Intent()

    interface ShowImageViewInterface {
        fun show(iv: ImageView, url: String)
    }

    fun setShowImageViewInterface(i: ShowImageViewInterface): PhotoViewer {
        mInterface = i
        return this
    }


    /**
     * 设置图片数据
     */
    fun setData(data: ArrayList<String>): PhotoViewer {
        i.putStringArrayListExtra(PIC_DATA, data)
        i.putExtra(TOTAL_PAGE, data.size)
        return this
    }

    /**
     * 设置图片之间的间距
     */
    fun setPicSpace(leftSpace: Int, topSpace: Int): PhotoViewer {
        i.putExtra(LEFT_SPACE, leftSpace)
        i.putExtra(TOP_SPACE, topSpace)
        return this
    }

    /**
     * 设置有多少列，用来计算间距
     */
    fun setCountOfRow(n: Int): PhotoViewer {
        i.putExtra(COUNT_ROW, n)
        return this
    }

    /**
     * 设置当前页， 从0开始
     */
    fun setCurrentPage(page: Int): PhotoViewer {
        i.putExtra(CURRENT_PAGE, page)
        return this
    }

    /**
     * 设置点击视图的坐标，用于回弹
     */
    fun setClickView(view: View): PhotoViewer {
        val location = IntArray(2)
        view.getLocationInWindow(location)
        i.putExtra(CLICK_VIEW, location)
        // 设置图片大小，获取测量的高度，因为宽可能不准
        // 如果设置一行三个，每一个imageView的宽度为100，那么gv就会自动增加每一个iv的宽度，所以取高度值
        i.putExtra(WIDTH, view.measuredHeight)
        i.putExtra(HEIGHT, view.measuredHeight)
        return this
    }


    fun start(context: Context) {
        i.setClass(context, PhotoViewerActivity::class.java)
        context.startActivity(i)
    }

}