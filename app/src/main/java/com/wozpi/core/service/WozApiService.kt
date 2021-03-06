package com.wozpi.core.service

import com.wozpi.core.BuildConfig
import com.wozpi.core.DemoApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class WozApiService {

    private var mRetrofit:Retrofit

    var mConnectTimeOut:Long = 60
    var mReadTimeOut:Long = 60
    var mWriteTimeOut:Long = 60


    init {
        /**
         * ---Show log http---
         * */
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = if(BuildConfig.DEBUG){
            HttpLoggingInterceptor.Level.BODY
        }else{
            HttpLoggingInterceptor.Level.NONE
        }

        val wozClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(mConnectTimeOut,TimeUnit.SECONDS)
            .readTimeout(mReadTimeOut,TimeUnit.SECONDS)
            .writeTimeout(mWriteTimeOut,TimeUnit.SECONDS)
            /**
             * set Authentication
             */
            .authenticator(WozAuthenticator(setNameHeader(),setAuthentication()))
            /**
             * handle callback use when call res error @Interceptor
             */
            .addInterceptor(WozErrorInterceptor())
            .build()


        mRetrofit = Retrofit.Builder()
            /**
             * Handle http
             * */
            .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
            /**
             * Mapping json to Object
             * */
            .addConverterFactory(GsonConverterFactory.create())
            .client(wozClient)
            .baseUrl(baseURL())
            .build()


    }


    protected open fun setAuthentication():String?{
        return null
    }

    protected open fun setNameHeader():String{
        return "Authorization"

    }


    protected fun baseURL():String{
        return "https://vnexpress.net"
    }


    fun <T> createService(cl:Class<T>): T{
        return mRetrofit.create(cl)
    }


}