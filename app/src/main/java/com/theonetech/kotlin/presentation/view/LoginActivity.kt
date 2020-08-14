package com.theonetech.kotlin.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.theonetech.kotlin.R
import com.theonetech.kotlin.databinding.ActivityLoginBinding
import com.theonetech.kotlin.domain.utils.NotificationClass
import com.theonetech.kotlin.presentation.viewmodel.LoginViewModel

/**
 * Created by Mahesh Keshvala on 28,July,2020
 */
class LoginActivity : AppCompatActivity() {
    private var loginBinding: ActivityLoginBinding? = null
    private var loginViewModel: LoginViewModel? = null
    private var notificationClass: NotificationClass = NotificationClass()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //bind layout for databinding
        loginBinding = DataBindingUtil.setContentView<ViewDataBinding>(
            this,
            R.layout.activity_login
        ) as ActivityLoginBinding?
        loginBinding?.lifecycleOwner = this
        loginViewModel = loginBinding?.let { LoginViewModel(this@LoginActivity, it) }
        loginBinding?.setVariable(1, loginViewModel)
        //init viewmodel
        loginBinding?.login = loginViewModel
        //live data observer to redirect user to dashboard screen
        loginViewModel?.loginLiveData?.observe(this, Observer { status ->
            if (status) {
                notificationClass.sendNotification(this@LoginActivity)
                val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

}