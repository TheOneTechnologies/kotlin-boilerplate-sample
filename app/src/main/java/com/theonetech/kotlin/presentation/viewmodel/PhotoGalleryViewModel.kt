package com.theonetech.kotlin.presentation.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.theonetech.kotlin.R
import com.theonetech.kotlin.data.connection.ApiClient
import com.theonetech.kotlin.data.connection.ApiInterface
import com.theonetech.kotlin.databinding.ActivityPhotoGalleryBinding
import com.theonetech.kotlin.domain.model.Albums
import com.theonetech.kotlin.domain.utils.SharedPrefUtils
import com.theonetech.kotlin.domain.utils.Utils
import com.theonetech.kotlin.presentation.adapter.AlbumListAdapter
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/**
 * Created by Amit on 4,Aug,2020
 */
@SuppressLint("StaticFieldLeak")
class PhotoGalleryViewModel(val context: Context, val binding: ActivityPhotoGalleryBinding) :
    ViewModel() {
    private lateinit var mLayoutManager: LinearLayoutManager
    private val mContext: Context = context
    private lateinit var arrayListAlbums: ArrayList<Albums>
    private lateinit var albumListAdapter: AlbumListAdapter
    private var pageNumber = 1
    private val pageSize = 20
    private var utils: Utils = Utils()

    init {
        initView()
        getAlbumList(pageNumber, pageSize)
    }

    //initialize view
    private fun initView() {
        binding.toolbar.textToolbarTitle.text =
            mContext.resources.getString(R.string.photo_gallery)
        binding.toolbar.imageBack.visibility = View.VISIBLE
        arrayListAlbums = ArrayList()
        mLayoutManager = LinearLayoutManager(mContext)
        binding.recyclerviewAlbumList.layoutManager = mLayoutManager
        albumListAdapter = AlbumListAdapter(mContext, arrayListAlbums)
        binding.recyclerviewAlbumList.adapter = albumListAdapter

        binding.toolbar.textLogout.setOnClickListener { Utils.logout(mContext) }
        binding.toolbar.imageBack.setOnClickListener {
            mContext as Activity
            mContext.finish()
        }
    }


    //method to fetch album list
    private fun getAlbumList(pageNumber: Int, pageSize: Int) {
        if (utils.isOnline(mContext)) {
            val apiService: ApiInterface? = ApiClient.getClient()?.create(
                ApiInterface::class.java
            )

            val call: Call<ResponseBody?>? = apiService?.getAlbumList(
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
                            val jsonArrayItems = jsonObject.getJSONArray("items")
                            for (i in 0 until jsonArrayItems.length()) {
                                val jsonObj = jsonArrayItems.getJSONObject(i)
                                val id = jsonObj.getInt("id")
                                val name = jsonObj.getString("name")
                                val photoUrl = jsonObj.getString("photoUrl")

                                val album = Albums(
                                    id, name, photoUrl
                                )
                                arrayListAlbums.add(album)
                                albumListAdapter.notifyDataSetChanged()

                            }
                            binding.progressbar.visibility = View.GONE


                        } else {
                            utils.onSNACK(
                                binding.rlMain,
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
                    binding.progressbar.visibility = View.GONE
                    t.printStackTrace()
                    utils.onSNACK(
                        binding.rlMain,
                        mContext,
                        t.message!!
                    )
                }
            })
        } else {
            binding.progressbar.visibility = View.GONE
            utils.onSNACK(
                binding.rlMain,
                mContext,
                mContext.resources.getString(R.string.error_msg_no_connectivity)
            )
        }

    }
}
