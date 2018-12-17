package com.wozpi.core.viewmodel

import android.arch.lifecycle.MutableLiveData
open class WozHolderViewModel<T> : WozViewModel() {
    var mDataHolder: MutableLiveData<T> = MutableLiveData()

    open fun getDataHolder():T?{
        return mDataHolder.value
    }
}