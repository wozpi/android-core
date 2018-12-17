package com.wozpi.core.view

import android.content.Context
import com.wozpi.core.R
import kotlinx.android.synthetic.main.dialog_error.*

class ErrorDialog(context: Context) : WozDialog(context) {

    private var valueMessageError: String? = null

    override fun initData() {
        when (valueMessageError) {
            null -> messageError.text = context.getString(R.string.unknown)
            else -> messageError.text = valueMessageError
        }
    }

    override fun setLayout(): Int {
        return R.layout.dialog_error
    }

    fun setMessage(value: String?): ErrorDialog {
        this.valueMessageError = value
        return this
    }

}