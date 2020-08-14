package com.theonetech.kotlin.domain.application

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

/**
 * Created by Mahesh Keshvala on 28,July,2020
 */
@SuppressLint("Registered")
class GlobalApplication : MultiDexApplication() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
