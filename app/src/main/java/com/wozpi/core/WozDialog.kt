package com.wozpi.core

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.Window

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

        initData()
    }

    abstract fun setLayout():Int

    abstract fun initData()
}