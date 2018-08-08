package com.wanglu.photoviewerlibrary

import android.content.Context
import android.util.TypedValue

object Utils{
    fun dp2px(context: Context, dipValue: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dipValue.toFloat(), context.resources.displayMetrics).toInt()
    }

}
