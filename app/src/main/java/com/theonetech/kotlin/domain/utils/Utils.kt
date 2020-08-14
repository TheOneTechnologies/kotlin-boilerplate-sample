package com.theonetech.kotlin.domain.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.theonetech.kotlin.R
import com.theonetech.kotlin.presentation.view.LoginActivity
import java.text.SimpleDateFormat



/**
 * Created by Mahesh Keshvala on 29,July,2020
 */
class Utils {
    companion object {
        const val LOGIN = "Auth/Login"
        const val HOMEWORKS = "HomeWorks"
        const val ALBUMS = "Albums"
        const val LOGIN_PREF = "login_pref"
        const val IS_LOGGED_IN = "is_logged_in"
        const val ACCESS_TOKEN = "access_token"
        const val TOKEN_TYPE = "token_type"



        //method to get day from date
        @SuppressLint("SimpleDateFormat")
        fun getDayFromString(strDate: String): String? {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val date = dateFormat.parse(strDate)
            val formatter = SimpleDateFormat("dd")
            return formatter.format(date)

        }

        //method to get month and year from date
        @SuppressLint("SimpleDateFormat")
        fun getMonthYearFromString(strDate: String): String? {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val date = dateFormat.parse(strDate)
            val formatter = SimpleDateFormat("MMM-yy")
            return formatter.format(date)
        }
        //method for logout
         fun logout(mContext:Context) {
            SharedPrefUtils.setBool(mContext, IS_LOGGED_IN, false)
            val intent = Intent(mContext, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            mContext as Activity
            mContext.startActivity(intent)
            mContext.finish()
        }

    }
    //method to display snackbar
    @RequiresApi(Build.VERSION_CODES.M)
    fun onSNACK(view: View, context: Context, msgText: String) {
        val snackBar = Snackbar.make(
            view, msgText,
            Snackbar.LENGTH_LONG
        )
        snackBar.setActionTextColor(context.getColor(R.color.colorPrimary))
        val snackbarView = snackBar.view
        snackbarView.setBackgroundColor(Color.WHITE)
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(context.getColor(R.color.colorPrimary))
        textView.textSize = 16f
        snackBar.show()
    }

    //method to show snackbar
    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

}