package com.wozpi.core.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import com.wozpi.core.WozUtils

abstract class WozDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(setLayout())
        if (window != null){
            window!!.setBackgroundDrawableResource(android.R.color.transparent)
            val wlp = window!!.attributes
            wlp.gravity = Gravity.CENTER
            window!!.setLayout((WozUtils.getWidthScreen(context) * 0.9).toInt(),wlp.height)
        }

        setCanceledOnTouchOutside(canBeTouchOutside())

        initData()
    }

    abstract fun setLayout():Int

    abstract fun initData()

    open fun canBeTouchOutside():Boolean{
        return true
    }
}