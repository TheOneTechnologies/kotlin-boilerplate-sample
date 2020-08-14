package com.theonetech.kotlin.domain.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


/**
 * Created by Mahesh Keshvala on 31,July,2020
 */
@SuppressLint("Registered")
class PermissionsClass(private val permissions: Array<String>, private var context: Context? = null) :
    AppCompatActivity() {

    val permissionAll = 1
    var isPermissionAll = true

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun checkPermissions() {
        ActivityCompat.requestPermissions(context as Activity, permissions, permissionAll)
    }

}