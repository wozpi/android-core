package com.wozpi.core.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.wozpi.core.event.CallbackViewModel
import com.wozpi.core.model.KindException
import com.wozpi.core.model.WozException
import com.wozpi.core.service.ApiCallback
import com.wozpi.core.service.RetrofitException
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

open class WozViewModel : ViewModel(),CallbackViewModel {

    private val tagApiDefault = -0x0001
    private var mWozException = MutableLiveData<WozException>()

    override fun onDestroy() {
        for (e in listApiService){
            e.unsubscribe()
        }
    }
    private var listApiService = ArrayList<Subscription>()

    fun getKindException(): LiveData<WozException>{
        return mWozException
    }


    /**
     * create and call Api
     * */

    protected fun <T> callApi(observableHolder: Observable<T>,callback: ApiCallback<T>,tag: Int = tagApiDefault){

        val holder = observableHolder
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                callback.getResult(result)
            }, { throwable ->
                // show error
                if(throwable is RetrofitException){
                    when(throwable.getKind()){
                        KindException.HTTP_422_WITH_DATA->{
                            //TODO do something
                            val error = WozException(KindException.HTTP_422_WITH_DATA,throwable.getErrorData(),tag)
                            mWozException.value = error
                        }
                        KindException.HTTP->{
                            //TODO do something
                            val error = WozException(KindException.HTTP,throwable.localizedMessage,tag)
                            mWozException.value = error

                        }
                        KindException.NETWORK->{
                            //TODO do something
                            Log.e("WOW","KindException.NETWORK")
                            val error = WozException(KindException.NETWORK,throwable.localizedMessage,tag)
                            mWozException.value = error
                        }
                        KindException.UNEXPECTED->{
                            //TODO do something
                            val error = WozException(KindException.UNEXPECTED,throwable.localizedMessage,tag)
                            mWozException.value = error
                        }
                    }
                }
            })

        listApiService.add(holder)
    }
}