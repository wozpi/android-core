package com.wozpi.core

import android.content.Context
import kotlinx.android.synthetic.main.dialog_error.*

class DemoDialog(context: Context) : WozDialog(context) {

    override fun initData() {
//        imgError.background = context.getDrawable(R.drawable.bg_error)
        imgError.clipToOutline = true
    }

    override fun setLayout(): Int {
        return R.layout.dialog_error
    }
}