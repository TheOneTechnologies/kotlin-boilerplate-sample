package com.theonetech.kotlin.presentation.view

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.theonetech.kotlin.BuildConfig
import com.theonetech.kotlin.data.connection.ApiClient
import com.theonetech.kotlin.data.connection.ApiInterface
import com.theonetech.kotlin.domain.utils.Utils
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.HttpURLConnection


/*
  Created by Amit on 14,Aug,2020
 */
@RunWith(AndroidJUnit4::class)
class HomeworkListActivityTest {
    private var mMockWebServer: MockWebServer = MockWebServer()
    private var mApiService: ApiInterface? = null

    @Rule
    @JvmField
    var activiHomeworkList = ActivityTestRule<HomeworkListActivity>(
        HomeworkListActivity::class.java
    )

    @Before
    fun setup() {
        try {
            mMockWebServer = MockWebServer()
            mMockWebServer.start()
            mApiService = ApiClient.getClient()?.create(
                ApiInterface::class.java
            )
            //Building the HTTPClient with the logger
            val httpClient = OkHttpClient.Builder()
            httpClient.build()
            mApiService = Retrofit.Builder()
                .baseUrl(mMockWebServer.url(BuildConfig.BASE_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
                .create(ApiInterface::class.java)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @After
    fun teardown() {
        try {
            mMockWebServer.shutdown()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Test
    fun testSuccessfulResponse() {
        //HomeWorkList API call with set valid user inputs to server
        //Get the valid server response
        val response = MockResponse()
        response.setResponseCode(HttpURLConnection.HTTP_OK)
        response.setBody(
            activiHomeworkList.activity.application.assets.open("homework_list.json").bufferedReader()
                .readLine()
        )
        mMockWebServer.enqueue(response)

        val mToken =
            "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMTNlMmQ0NC00ZDE2LTQ0N2UtYTkwNy1iYTEwOWY3MjY2MWEiLCJqdGkiOiIyMDY2ZmEwYy0zN2I2LTRhYzQtYTk5MS1hMGFiYmQ0YWIyMTgiLCJpYXQiOjE1OTc0MTQ2ODgsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL25hbWUiOiJzdHVkZW50MTFfMUEiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9naXZlbm5hbWUiOiJTdHVkZW50IFNjaG9vbCAxXzFBIiwiU0NIX0lEIjoiMSIsIlNURF9JRCI6IjEiLCJESVZfSUQiOiIzIiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy9yb2xlIjoiU3R1ZGVudCIsIm5iZiI6MTU5NzQxNDY4OCwiZXhwIjoxNTk3NTAxMDg4LCJpc3MiOiJQb3J0YWxBdXRoZW50aWNhdGlvblNlcnZpY2UiLCJhdWQiOiJodHRwOi8vbG9jYWxob3N0OjUwMDEvIn0.swQ39yDNlptQD1SMDjNG_kLr6ITu7ksO4VZ9CL9Dzl8"

        val apiResponse: Response<Any?>? = null

        try {
            val call: Call<ResponseBody?>? = mApiService?.getHomeWorkList(mToken, 1, 20)
            call?.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(
                    call: Call<ResponseBody?>,
                    response: Response<ResponseBody?>
                ) {
                    assertEquals(response.code(), 200)
                    assertEquals(true, response.isSuccessful)
                }

                override fun onFailure(
                    call: Call<ResponseBody?>,
                    t: Throwable
                ) {
                    t.printStackTrace()
                }
            })
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Test
    fun testFailedResponse() {
        val dispatcher: Dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                if (request.path == Utils.ALBUMS) {
                    return MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
                        .setBody(
                            activiHomeworkList.activity.application.assets.open("homework_list.json")
                                .bufferedReader()
                                .readLine()
                        )
                }
                throw IllegalStateException(" " + request.path)
            }
        }

        mMockWebServer.dispatcher = dispatcher
    }

}