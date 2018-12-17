package com.wozpi.core

import android.content.Context
import android.view.View
import com.wozpi.core.view.WozRecyclerViewAdapter
import com.wozpi.core.view.WozRecyclerViewHolder
import kotlinx.android.synthetic.main.item_demo_v2.view.*

class AdapterDemoV2(context: Context) : WozRecyclerViewAdapter<Int>(context){
    override fun setLayout(viewType: Int): Int {
        return R.layout.item_demo
    }

    override fun setViewHolder(viewRoot: View, viewType: Int): WozRecyclerViewHolder<*> {
        return DemoHolder(viewRoot)
    }

    override fun onBindViewHolder(holder: WozRecyclerViewHolder<*>, position: Int) {
        super.onBindViewHolder(holder, position)
        if(holder is DemoHolder){
            holder.bindData(position.toString())
        }
    }


    override fun getItemCount(): Int {
        return 30
    }

    private class DemoHolder(itemView:View): WozRecyclerViewHolder<String>(itemView,BR.itemViewModel,ItemViewModel())
}