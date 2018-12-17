package com.wozpi.core.view

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View
import com.wozpi.core.viewmodel.WozHolderViewModel

open class WozRecyclerViewHolder<T>: RecyclerView.ViewHolder{

    private val mBinding:ViewDataBinding? = DataBindingUtil.bind(itemView)
    private var mViewModel:WozHolderViewModel<T>? = null


    constructor(itemView: View):super(itemView)

    constructor(itemView: View,name:Int, viewModel: WozHolderViewModel<T>):super(itemView){
        mViewModel = viewModel
        mBinding!!.setVariable(name,viewModel)
    }

    open fun bindData(data:T){
        if(mViewModel != null)
            mViewModel!!.mDataHolder.value = (data)
    }
}