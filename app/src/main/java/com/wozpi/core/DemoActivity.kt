package com.wozpi.core

import android.arch.lifecycle.Observer
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.wozpi.core.databinding.ActivityMainBinding
import com.wozpi.core.event.CallbackLoadMore
import com.wozpi.core.model.WozException
import com.wozpi.core.view.WozActivity
import com.wozpi.core.viewmodel.WozViewModel
import kotlinx.android.synthetic.main.activity_main.*

class DemoActivity : WozActivity<ActivityMainBinding>() {


    private val demoAdapter = AdapterDemo(this)
    private var status = false

    override fun initData() {
        val layout = LinearLayoutManager(this)
        layout.isSmoothScrollbarEnabled = false
        layout.reverseLayout = false
        layout.stackFromEnd = false
        listData.layoutManager = layout
        listData.adapter = demoAdapter

        val temp = setViewModel(BR.mainViewModel,DemoViewModel::class.java)

        temp.getProducts().observe(this, Observer<ArrayList<Int>> {
            demoAdapter.setData(it!!)
            Log.e("WOW","load onchange")
           status = !status
        })


//        demoAdapter.registerLoadMore(listData,object : CallbackLoadMore{
//            override fun onLoadMore() {
//
//                temp.buttonClick()
//            }
//
//        })

//        temp.getProfile()

    }


    override fun setLayoutView(): Int {
        return R.layout.activity_main
    }



    override fun onRetryConnection(tag: Int) {
        Log.e("WOW","inside network: ")
        demoAdapter.openNoInternet()
    }
}