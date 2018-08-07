package com.wanglu.photoviewerlibrary

import android.view.View

class WrapperView(private val mTarget: View) {

    var width: Int
        get() = mTarget.layoutParams.width
        set(width) {
            mTarget.layoutParams.width = width
            mTarget.requestLayout()
        }

    var height: Int
        get() = mTarget.layoutParams.height
        set(height) {
            mTarget.layoutParams.height = height
            mTarget.requestLayout()
        }

    var translationX: Float
        get() = mTarget.translationX
        set(height) {
            mTarget.translationX = translationX
            mTarget.requestLayout()
        }

    var translationY: Float
        get() = mTarget.translationY
        set(height) {
            mTarget.translationY = translationY
            mTarget.requestLayout()
        }
}
