package com.wozpi.core

import android.content.Context
import android.net.ConnectivityManager
import android.text.TextUtils
import android.view.WindowManager
import android.util.DisplayMetrics
import android.util.TypedValue
import java.net.InetAddress

class WozUtils {

    companion object {

        fun isNetworkConnected(context: Context):Boolean{
            val cManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cManager.activeNetworkInfo != null
        }

        fun isInternetAvailable():Boolean{
            return try {
                val ipAddress = InetAddress.getByName("www.google.com")

                !TextUtils.isEmpty(ipAddress.hostName)

            } catch (e: Exception) {
                false
            }

        }

        fun getWidthScreen(context: Context):Int{
            val displayMetrics = DisplayMetrics()
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            return displayMetrics.widthPixels
        }

        fun convertDpToPx(value:Float, context: Context):Float{
            val dMetrics = context.resources.displayMetrics
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,dMetrics)
        }

    }


}