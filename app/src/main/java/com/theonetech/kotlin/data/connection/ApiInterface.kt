package com.theonetech.kotlin.data.connection

import com.google.gson.JsonObject
import com.theonetech.kotlin.domain.utils.Utils
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Mahesh Keshvala on 28,July,2020
 */
 interface ApiInterface {
    @POST(Utils.LOGIN)
    fun login(@Body jsonObject: JsonObject?): Call<ResponseBody?>?

    @GET(Utils.HOMEWORKS)
    fun getHomeWorkList(@Header("Authorization")token:String,@Query("pageNumber")pagenumber:Int, @Query("pageSize")pageSize:Int): Call<ResponseBody?>?

    @GET(Utils.ALBUMS)
    fun getAlbumList(@Header("Authorization")token:String,@Query("pageNumber")pagenumber:Int, @Query("pageSize")pageSize:Int): Call<ResponseBody?>?
}