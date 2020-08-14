package com.theonetech.kotlin.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.theonetech.kotlin.R
import com.theonetech.kotlin.databinding.ActivityDashboardBinding
import com.theonetech.kotlin.presentation.viewmodel.DashboardViewModel

class DashboardActivity : AppCompatActivity() {
    private var activityDashboardBinding: ActivityDashboardBinding? = null
    private var dashboardViewModel: DashboardViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //bind layout for databinding
        activityDashboardBinding = DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_dashboard) as ActivityDashboardBinding?
        activityDashboardBinding?.lifecycleOwner = this
        //init viewmodel
        dashboardViewModel = this.activityDashboardBinding?.let { DashboardViewModel(this, it) }
        activityDashboardBinding?.dashboard = dashboardViewModel
        activityDashboardBinding!!.setVariable(0,dashboardViewModel)
    }
}