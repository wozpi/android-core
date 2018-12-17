package com.wozpi.core.event

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log

abstract class EndlessRecyclerViewScrollListener(layoutManager: RecyclerView.LayoutManager) : RecyclerView.OnScrollListener() {
    private var mIsLoading = false

    /**
     * Number is to call load more
     * */
    private var mVisibleThreshold = 2

    /**
     * Total item before
     * */
    private var mPreviousTotalItemCount = 0

    private val mLayoutManager = layoutManager

    init {
        if (mLayoutManager is StaggeredGridLayoutManager){
            mVisibleThreshold *= mLayoutManager.spanCount
        }else{
            if(mLayoutManager is GridLayoutManager){
                mVisibleThreshold *= mLayoutManager.spanCount
            }
        }
    }

    private fun getLastVisibleItem(lastVisibleItemPositions : IntArray):Int{
        var maxSize = 0
        val length = lastVisibleItemPositions.size
        if (length > 0) {
            maxSize = lastVisibleItemPositions[0]
        }
        for (i in 1..length){
            if (maxSize  < lastVisibleItemPositions[i]){
                maxSize = lastVisibleItemPositions[i]
            }
        }

        return maxSize
    }

    fun resetState(){
        this.mPreviousTotalItemCount = 0
        this.mIsLoading = true
    }

    abstract fun onLoadMore()

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        var lastVisibleItemPosition = 0
        val totalItemCount = mLayoutManager.itemCount

        when (mLayoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions = mLayoutManager.findLastVisibleItemPositions(null)
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
            }
            is GridLayoutManager -> lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition()
            is LinearLayoutManager -> lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition()
        }

        if(totalItemCount < mPreviousTotalItemCount){
            mPreviousTotalItemCount = totalItemCount
            if(totalItemCount == 0){
                mIsLoading = true
            }
        }

        if(mIsLoading && (totalItemCount > mPreviousTotalItemCount)){
            mIsLoading = false
            mPreviousTotalItemCount = totalItemCount
        }

        if(!mIsLoading && (lastVisibleItemPosition + 1 + mVisibleThreshold) >= totalItemCount){
            mIsLoading = true
            onLoadMore()
        }
    }
}