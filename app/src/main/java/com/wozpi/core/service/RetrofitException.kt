package com.wozpi.core.service

import com.wozpi.core.model.KindException
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber
import java.io.IOException
import java.lang.RuntimeException

class RetrofitException(message: String,private val mUrl: String?,
                        private val mResponse: Response<*>?,private val mKind: KindException,
                        private val mThrowable: Throwable?,private val mRetrofit: Retrofit?) : RuntimeException(message) {

    /** The data returned from the server in the response body*/
    private var mServerError: ServerError? = null

    fun getErrorData(): ServerError?{
        return mServerError
    }

    fun getKind() = mKind

    companion object {
        fun httpError(url: String, response: Response<*>, retrofit: Retrofit):RetrofitException{
            val message = response.code().toString() + " " + response.message()
            return RetrofitException(message,url,response,KindException.HTTP,null,retrofit)
        }

        fun httpErrorWithObject(url: String, response: Response<*>, retrofit: Retrofit):RetrofitException{
            val message = response.code().toString() + " " + response.message()
            val errorException = RetrofitException(message,url,response,KindException.HTTP_422_WITH_DATA,null,retrofit)
            errorException.deserializeServerError()
            return errorException
        }

        fun networkError(throwable: Throwable): RetrofitException{
            return RetrofitException(throwable.localizedMessage,null,null,KindException.NETWORK,throwable,null)
        }



        fun unexpectedError(throwable: Throwable): RetrofitException{
            return RetrofitException(throwable.localizedMessage,null,null,KindException.UNEXPECTED,throwable,null)
        }
    }


    private fun deserializeServerError(){
        if(mResponse?.errorBody() != null){
            try {
                mServerError = getBodyErrorAs(ServerError::class.java)
            }catch (e:IOException){
                Timber.e(e)
            }
        }
    }

    private fun <T> getBodyErrorAs(type: Class<T>): T?{
        return if(mResponse?.errorBody() == null || mRetrofit == null ){
            null
        }else{
            val converter: Converter<ResponseBody,T> = mRetrofit.responseBodyConverter(type, arrayOfNulls<Annotation>(0))
            converter.convert(mResponse.errorBody()!!)
        }
    }
}