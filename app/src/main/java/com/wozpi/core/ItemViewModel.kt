package com.wozpi.core

import android.databinding.ObservableField
import com.wozpi.core.viewmodel.WozViewModel

class ItemViewModel : WozViewModel() {
    var mName: ObservableField<String> = ObservableField()

//    init {
//        mName.set("gogogo")
//    }
//
    fun setValueName(mValue:String){
        mName.apply {
            this.set(mValue)
        }
    }
}