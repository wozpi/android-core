package com.wozpi.core

import android.content.Context
import android.support.v7.widget.*
import android.view.Gravity
import android.view.View
import com.wozpi.core.view.WozRecyclerViewAdapter
import com.wozpi.core.view.WozRecyclerViewHolder
import kotlinx.android.synthetic.main.item_demo_v2.view.*

open class AdapterDemo(context: Context) : WozRecyclerViewAdapter<Int>(context){

    var mSnapHelper:SnapHelper = LinearSnapHelper()
    var pool:RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    override fun setLayout(viewType: Int): Int {
        if(viewType == 9){
            return R.layout.item_demo_v2
        }
        return R.layout.item_demo
    }

    override fun getCustomItemViewType(position: Int): Int {
        if(position %2 == 0){
            return 9
        }

        return super.getCustomItemViewType(position)
    }

    override fun setViewHolder(viewRoot: View, viewType: Int): WozRecyclerViewHolder<*> {
        if (viewType == 9){
            return DemoHeaderHolder(viewRoot)
        }
        return DemoHolder(viewRoot)
    }

    override fun onBindViewHolder(holder: WozRecyclerViewHolder<*>, position: Int) {
        super.onBindViewHolder(holder, position)
        if(holder is DemoHolder){
            holder.bindData(position.toString())

        }
        if(holder is DemoHeaderHolder){
//            holder.bindData(position.toString())
//            mSnapHelper.attachToRecyclerView(holder.getList())
//            holder.getList().setRecycledViewPool(pool)
        }
    }

    private open class DemoHeaderHolder(itemView:View): WozRecyclerViewHolder<String>(itemView,BR.itemViewModel,ItemViewModel()){
        init {
            itemView.lisTemp.layoutManager = LinearLayoutManager(itemView.context,LinearLayoutManager.HORIZONTAL,false)
            itemView.lisTemp.setHasFixedSize(true)
            itemView.lisTemp.adapter = AdapterDemoV2(itemView.context)
        }

        override fun bindData(data: String) {

            super.bindData(data)

        }

        open fun getList():RecyclerView{
            return itemView.lisTemp
        }
    }

    private class DemoHolder(itemView:View): WozRecyclerViewHolder<String>(itemView,BR.itemViewModel,ItemViewModel())
}