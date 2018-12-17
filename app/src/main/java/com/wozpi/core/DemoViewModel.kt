package com.wozpi.core

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import android.util.Log
import com.wozpi.core.service.ApiCallback
import com.wozpi.core.viewmodel.WozViewModel


class DemoViewModel : WozViewModel(){

    enum class WWW(val value: Int){
        HELLO(1)
    }
    var name:ObservableField<String> = ObservableField("Go Pdypham")

    var items : MutableLiveData<ArrayList<Int>> = MutableLiveData()

    var mUser = User()
    var user:ObservableField<User> = ObservableField()

    fun getNameBeautiful():String{
        return name.get()!!
    }

    fun setNameBeautiful(value:String){
        this.name .set(value)
    }

    fun getProfile(){

//        callApi(DemoApiService.instances.getUserService().getProfile(),object : ApiCallback<User>{
//
//            override fun getResult(data: User) {
//                if(items.value == null){
//                    items.value = ArrayList(List(10) { index -> 2 * index })
//                }else {
//                    items.value!!.addAll(List(10) { index -> 2 * index })
//                }
//                Log.e("WOW","getResult")
//            }
//
//        })
        if(items.value == null){
            items.value = ArrayList(List(1) { index -> 2 * index })
        }else {
//            items.value!!.addAll(List(1) { index -> 2 * index })
            items.value = ArrayList(List(1) { index -> 2 * index })
        }
    }

    fun getProducts(): LiveData<ArrayList<Int>> {
        return items
    }
    fun buttonClick(){
//        getProfile()
        name.set("fuck")
        mUser.name = "LOL"
//        val mUser = User()
//        mUser.name = "King"
//        user.set(mUser)
//        items.value = List(10) {index -> 2 * index }

    }


}