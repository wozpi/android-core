package com.wozpi.core

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.View
import com.wozpi.core.view.WozRecyclerViewAdapter
import com.wozpi.core.view.WozRecyclerViewHolder

class AdapterDemo(context: Context) : WozRecyclerViewAdapter<Int>(context){
    override fun setLayout(viewType: Int): Int {
        return R.layout.item_demo
    }

    override fun setViewHolder(viewRoot: View, viewType: Int): WozRecyclerViewHolder {
        return DemoHolder(viewRoot)
    }

    private class DemoHolder(itemView:View): WozRecyclerViewHolder(itemView){
        var mBind: ViewDataBinding? = DataBindingUtil.bind(itemView)
        var mItemViewModel = ItemViewModel()
        init {
            mBind!!.setVariable(BR.itemViewModel,mItemViewModel)
        }
        fun bindData(pos:Int){
            mItemViewModel.setValueName(pos.toString())
        }
    }
}