package com.theonetech.kotlin.presentation.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.theonetech.kotlin.R
import com.theonetech.kotlin.data.connection.ApiClient
import com.theonetech.kotlin.data.connection.ApiInterface
import com.theonetech.kotlin.databinding.ActivityHomeworkListBinding
import com.theonetech.kotlin.domain.model.Homework
import com.theonetech.kotlin.domain.utils.PaginationScrollListener
import com.theonetech.kotlin.domain.utils.SharedPrefUtils
import com.theonetech.kotlin.domain.utils.Utils
import com.theonetech.kotlin.presentation.adapter.HomeworkListAdapter
import com.theonetech.kotlin.presentation.view.LoginActivity
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/**
 * Created by Amit on 31,July,2020
 */

class HomeworkListViewModel(context: Context, binding: ActivityHomeworkListBinding) : ViewModel() {
    private val homeworkListBinding: ActivityHomeworkListBinding = binding
    lateinit var mLayoutManager: LinearLayoutManager

    @SuppressLint("StaticFieldLeak")
    private val mContext: Context = context
    private lateinit var arrayListHomework: ArrayList<Homework>
    private lateinit var homeworkListAdapter: HomeworkListAdapter
    private var pageNumber = 1
    private val pageSize = 10
    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    private var total: Int = 0
    private var utils: Utils = Utils()

    init {
        initView()
        getHomeworkList(pageNumber, pageSize)
    }

    //initialize view
    private fun initView() {
        homeworkListBinding.toolbar.textToolbarTitle.text =
            mContext.resources.getString(R.string.homework)
        homeworkListBinding.toolbar.imageBack.visibility = View.VISIBLE
        arrayListHomework = ArrayList()
        mLayoutManager = LinearLayoutManager(mContext)
        homeworkListBinding.recyclerviewHomeworkList.layoutManager = mLayoutManager
        homeworkListAdapter = HomeworkListAdapter(mContext, arrayListHomework)
        homeworkListBinding.recyclerviewHomeworkList.adapter = homeworkListAdapter

        homeworkListBinding.recyclerviewHomeworkList.addOnScrollListener(object :
            PaginationScrollListener(mLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
            //method for pagination
            override fun loadMoreItems() {
                if (arrayListHomework.size < total) {
                    isLoading = true
                    homeworkListBinding.progressbar.visibility = View.VISIBLE
                    pageNumber++
                    Handler().postDelayed({ getHomeworkList(pageNumber, pageSize) }, 1000)
                }


            }
        })

        homeworkListBinding.toolbar.textLogout.setOnClickListener { Utils.logout(mContext) }
        homeworkListBinding.toolbar.imageBack.setOnClickListener {
            mContext as Activity
            mContext.finish()
        }
    }


    //method to fetch homework list
    private fun getHomeworkList(pageNumber: Int, pageSize: Int) {
        if (utils.isOnline(mContext)) {
            val apiService: ApiInterface? = ApiClient.getClient()?.create(
                ApiInterface::class.java
            )

            val call: Call<ResponseBody?>? = apiService?.getHomeWorkList(
                SharedPrefUtils.getStr(mContext, Utils.TOKEN_TYPE)!! + " " + SharedPrefUtils.getStr(
                    mContext,
                    Utils.ACCESS_TOKEN
                )!!,
                pageNumber,
                pageSize
            )
            call?.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(
                    call: Call<ResponseBody?>,
                    response: Response<ResponseBody?>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val jsonObject = JSONObject(response.body()!!.string())
                            total = jsonObject.getString("total").toInt()
                            val jsonArrayItems = jsonObject.getJSONArray("items")
                            for (i in 0 until jsonArrayItems.length()) {
                                val jsonObj = jsonArrayItems.getJSONObject(i)
                                val id = jsonObj.getInt("id")
                                val userName = jsonObj.getString("userName")
                                val subjectName = jsonObj.getString("subjectName")
                                val date = jsonObj.getString("date")
                                val schoolId = jsonObj.getInt("schoolId")
                                val standardId = jsonObj.getInt("standardId")
                                val subjectId = jsonObj.getInt("subjectId")
                                val boardId = jsonObj.getInt("boardId")
                                val divisionId = jsonObj.optInt("divisionId")
                                val dueDate = jsonObj.getString("dueDate")
                                val description = jsonObj.getString("description")
                                val hasAttachment = jsonObj.getBoolean("hasAttachment")
                                val homework = Homework(
                                    id,
                                    userName,
                                    subjectName,
                                    date,
                                    schoolId,
                                    standardId,
                                    subjectId,
                                    boardId,
                                    divisionId,
                                    dueDate,
                                    description,
                                    hasAttachment
                                )
                                arrayListHomework.add(homework)
                                homeworkListAdapter.notifyDataSetChanged()

                            }
                            homeworkListBinding.progressbar.visibility = View.GONE
                            isLoading = false


                        } else {
                            utils.onSNACK(
                                homeworkListBinding.rlMain,
                                mContext,
                                mContext.resources.getString(R.string.err_msg)
                            )
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBody?>,
                    t: Throwable
                ) {
                    homeworkListBinding.progressbar.visibility = View.GONE
                    t.printStackTrace()
                    utils.onSNACK(
                        homeworkListBinding.rlMain,
                        mContext,
                        t.message!!
                    )
                }
            })
        } else {
            homeworkListBinding.progressbar.visibility = View.GONE
            utils.onSNACK(
                homeworkListBinding.rlMain,
                mContext,
                mContext.resources.getString(R.string.error_msg_no_connectivity)
            )
        }

    }
}
