package com.wozpi.core

import android.content.Context
import android.view.WindowManager
import android.util.DisplayMetrics
import android.util.TypedValue

class WozUtils {

    companion object {

        fun getWidthScreen(context: Context):Int{
            val displayMetrics = DisplayMetrics()
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            return displayMetrics.widthPixels
        }

        fun convertDpToPx(value:Float, context: Context):Float{
            val dMetrics = context.resources.displayMetrics
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,dMetrics)
        }

    }


}