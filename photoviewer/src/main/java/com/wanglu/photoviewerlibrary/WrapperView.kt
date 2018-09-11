package com.wanglu.photoviewerlibrary

import android.view.View

class WrapperView(private val mTarget: View) {


    fun getWidth(): Int {
        return mTarget.layoutParams.width
    }

    fun setWidth(width: Int) {
        mTarget.layoutParams.width = width
        mTarget.requestLayout()
    }

}