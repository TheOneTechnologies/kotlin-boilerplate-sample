package com.theonetech.kotlin.presentation.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.theonetech.kotlin.R
import com.theonetech.kotlin.domain.utils.PermissionsClass
import com.theonetech.kotlin.domain.utils.SharedPrefUtils
import com.theonetech.kotlin.domain.utils.Utils


/**
 * Created by Mahesh Keshvala on 28,July,2020
 */
class SplashActivity : AppCompatActivity() {

    private var permissions = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    private val permissionVal = PermissionsClass(permissions, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        permissionVal.checkPermissions()

    }

    private fun init() {
        Handler().postDelayed({
            if (SharedPrefUtils.getBool(this, Utils.IS_LOGGED_IN)!!) {
                val intent = Intent(
                    this@SplashActivity,
                    DashboardActivity::class.java
                ) //Redirect to dashboard screen after 2 seconds
                startActivity(intent)
                finish()
            }
            else
            {
                val intent = Intent(
                    this@SplashActivity,
                    LoginActivity::class.java
                ) //Redirect to login screen after 2 seconds
                startActivity(intent)
                finish()
            }

        }, 2000)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionVal.permissionAll) {
            for (element in grantResults) {
                if (element != PackageManager.PERMISSION_GRANTED) { // permission was granted, do your work...
                    permissionVal.isPermissionAll = false
                    break
                } else {
                    permissionVal.isPermissionAll = true
                }
            }
            if (!permissionVal.isPermissionAll) {
                // permission denied
                // Disable the functionality that depends on this permission.
                permissionVal.checkPermissions()
            } else {
                init()
            }
        }
    }
}