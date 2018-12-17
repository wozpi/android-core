package com.wozpi.core.view

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wozpi.core.R
import com.wozpi.core.event.CallbackLoadMore
import com.wozpi.core.event.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.view_load_more.view.*

private const val TYPE_PROGRESS_LOAD_MORE = 0x0001
private const val TYPE_NO_DATA = 0x0002
private const val TYPE_NO_INTERNET = 0x0003
private const val TYPE_ITEM = 0x0004

abstract class WozRecyclerViewAdapter <T>(context: Context) : RecyclerView.Adapter<WozRecyclerViewHolder>() {


    private var mData = ArrayList<T>()
    private var mContext: Context = context
    private var mIsNoInternet = false
    /**
     * Do you want to load more make it true
     * */
    private var isRegisterLoadMore = false

    /**
     * End load more?
     * */
    private var mIsReachEnd = false


    /**
     * start override
     * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WozRecyclerViewHolder {

        return when(viewType){
            TYPE_PROGRESS_LOAD_MORE->{
                val viewRoot = LayoutInflater.from(mContext).inflate(setLayoutLoadMore(),parent,false)
                LoadMoreViewHolder(viewRoot)
            }
            TYPE_NO_DATA->{
                val viewRoot = LayoutInflater.from(mContext).inflate(setLayoutNoData(),parent,false)
                NoDataViewHolder(viewRoot)
            }
            TYPE_NO_INTERNET->{
                val viewRoot = LayoutInflater.from(mContext).inflate(setLayoutNoInternet(),parent,false)
                NoDataViewHolder(viewRoot)
            }
            else->{
                val viewRoot = LayoutInflater.from(mContext).inflate(setLayout(viewType),parent,false)
                setViewHolder(viewRoot,viewType)
            }

        }
    }


    override fun onBindViewHolder(holder: WozRecyclerViewHolder, position: Int) {
        if (holder is LoadMoreViewHolder){
            holder.isShowViewProcess(position == getBottomItemPosition() && isRegisterLoadMore && !mIsReachEnd)
        }

    }


    override fun getItemViewType(position: Int): Int {
        return if(mIsNoInternet){
            TYPE_NO_INTERNET
        }else if(mData.size <= 0){
            TYPE_NO_DATA
        }else if (position == getBottomItemPosition() && isRegisterLoadMore) {
            TYPE_PROGRESS_LOAD_MORE
        } else getCustomItemViewType(position)
    }

    override fun getItemCount(): Int {
        return if(mData.size <= 0 || mIsNoInternet){
            1
        }else{
            if (isRegisterLoadMore){
                mData.size + 1
            }else{
                mData.size
            }
        }
    }

    /**
     * end override
     * */

    /**
     * start abstract
     * */
    protected abstract fun setLayout(viewType: Int): Int

    protected abstract fun setViewHolder(viewRoot: View, viewType: Int): WozRecyclerViewHolder

    /**
     * end abstract
     * */

    fun openNoInternet(){
        mIsNoInternet = true
        notifyDataSetChanged()
    }

    fun closeNoInternet(){
        mIsNoInternet = false
        notifyDataSetChanged()
    }

    private fun getBottomItemPosition(): Int{
        return itemCount - 1
    }

    protected open fun getCustomItemViewType(position: Int): Int {
        return TYPE_ITEM
    }

    protected open fun setLayoutLoadMore(): Int{
        return R.layout.view_load_more
    }

    protected open fun setLayoutNoData(): Int{
        return R.layout.view_no_data
    }

    protected open fun setLayoutNoInternet(): Int{
        return R.layout.view_no_internet
    }

    /**
     * Register to load more
     *
     * NOTE: recyclerView must be set layout manager before register load more
     */

    fun registerLoadMore(recyclerView: RecyclerView, callback: CallbackLoadMore){

        isRegisterLoadMore = true
        val currentLayoutManager =  recyclerView.layoutManager
        if(currentLayoutManager != null) {

            if(currentLayoutManager is GridLayoutManager) {

                currentLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
                    override fun getSpanSize(position: Int): Int {
                        return when(getItemViewType(position)){
                            TYPE_PROGRESS_LOAD_MORE-> {
                                currentLayoutManager.spanCount
                            }
                            TYPE_ITEM-> 1
                            else -> {
                                0
                            }
                        }
                    }

                }

            }


            val endlessRecyclerViewScrollListener = object : EndlessRecyclerViewScrollListener(currentLayoutManager) {
                override fun onLoadMore() {
                    if(!mIsReachEnd)
                        callback.onLoadMore()
                }

            }

            recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener)
        }


    }

    /**
     * Get list data
     * */
    fun getData() = mData

    /**
     * Get context
     * */
    fun getContext() = mContext

    fun setData(data:List<T>){
        mData.addAll(data)
        this.notifyDataSetChanged()
    }

    fun clearAll(){
        mData.clear()
        this.notifyDataSetChanged()
    }

    private class LoadMoreViewHolder(itemView: View) : WozRecyclerViewHolder(itemView) {

        fun isShowViewProcess(isShow: Boolean){
            itemView.viewProcess.visibility = if(isShow) View.VISIBLE else View.GONE
        }
    }

    private class NoDataViewHolder(itemView: View) : WozRecyclerViewHolder(itemView)
}