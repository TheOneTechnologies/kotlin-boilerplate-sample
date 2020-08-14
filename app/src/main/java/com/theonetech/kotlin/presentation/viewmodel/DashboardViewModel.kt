package com.theonetech.kotlin.presentation.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import com.theonetech.kotlin.R
import com.theonetech.kotlin.databinding.ActivityDashboardBinding
import com.theonetech.kotlin.domain.interfaces.DashboardItemClickListener
import com.theonetech.kotlin.domain.model.Category
import com.theonetech.kotlin.domain.utils.SharedPrefUtils
import com.theonetech.kotlin.domain.utils.SpacesItemDecoration
import com.theonetech.kotlin.domain.utils.Utils
import com.theonetech.kotlin.presentation.adapter.CategoryAdapter
import com.theonetech.kotlin.presentation.view.HomeworkListActivity
import com.theonetech.kotlin.presentation.view.LoginActivity
import com.theonetech.kotlin.presentation.view.PhotoGalleryActivity
import java.util.*

/**
 * Created by Mahesh Keshvala on 28,July,2020
 */
@SuppressLint("StaticFieldLeak")
class DashboardViewModel(
    private val mContext: Context,
    private val binding: ActivityDashboardBinding
) : ViewModel(), DashboardItemClickListener, View.OnClickListener {
    @SuppressLint("StaticFieldLeak")
    private val activityDashboardBinding: ActivityDashboardBinding = binding
    private val categoryNameArray = arrayOf(
        "Homework",
        "News",
        "Photo Gallery",
        "Calendar",
        "Birthdays",
        "TimeTable"
    )
    private val categoryImageArray = intArrayOf(
        R.drawable.ic_homework,
        R.drawable.ic_news,
        R.drawable.ic_photo_gallery,
        R.drawable.ic_calendar,
        R.drawable.ic_birthday,
        R.drawable.ic_calendar
    )
    private var categoryArrayList: ArrayList<Category>? = null
    private var categoryAdapter: CategoryAdapter? = null

    init {
        initView()
    }

    //method to initialize view.
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initView() {
        activityDashboardBinding.llDrawer.isClickable = true
        activityDashboardBinding.toolbar.imageBack.setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_drawer))
        activityDashboardBinding.toolbar.textToolbarTitle.text =
            mContext.resources.getString(R.string.dashboard)
        activityDashboardBinding.toolbar.textLogout.visibility = View.GONE
        activityDashboardBinding.recyclerviewDashboard.layoutManager = GridLayoutManager(
            mContext,
            3

        )
        binding.recyclerviewDashboard.addItemDecoration(SpacesItemDecoration(15))
        categoryArrayList = ArrayList<Category>()
        categoryAdapter = CategoryAdapter(mContext, categoryArrayList!!, this)
        activityDashboardBinding.recyclerviewDashboard.adapter = categoryAdapter

        loadDummyData()

        //click listener
        registerListener()


    }
    //method to register listener
    private fun registerListener() {
        activityDashboardBinding.textDashboardLogout.setOnClickListener(this)
        activityDashboardBinding.toolbar.imageBack.setOnClickListener(this)
        activityDashboardBinding.textDashboard.setOnClickListener(this)
        activityDashboardBinding.textSettings.setOnClickListener(this)
    }



    //Method for setting dummy data in dashboard.
    private fun loadDummyData() {
        for (i in categoryNameArray.indices) {
            val category = Category(categoryNameArray[i], categoryImageArray[i])
            categoryArrayList!!.add(category)
            categoryAdapter!!.notifyDataSetChanged()
        }
    }

    //dashbaord item click listener
    override fun onDashboardItemClick(item: String) {
        when(item)
        {
            "Homework"->{
                val intent = Intent(mContext, HomeworkListActivity::class.java)
                mContext.startActivity(intent)
            }
            "Photo Gallery"->{
                val intent = Intent(mContext, PhotoGalleryActivity::class.java)
                mContext.startActivity(intent)
            }
        }

    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.text_dashboard_logout -> {
                activityDashboardBinding.drawerlayout.closeDrawer(activityDashboardBinding.llDrawer)
                Utils.logout(mContext)
            }
            R.id.image_back -> {
                activityDashboardBinding.drawerlayout.openDrawer(activityDashboardBinding.llDrawer)
            }
            R.id.text_dashboard -> {
                activityDashboardBinding.drawerlayout.closeDrawer(activityDashboardBinding.llDrawer)
            }
            R.id.text_settings -> {
                activityDashboardBinding.drawerlayout.closeDrawer(activityDashboardBinding.llDrawer)
            }
        }
    }
}