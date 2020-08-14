package com.theonetech.kotlin.presentation.view

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.google.gson.JsonObject
import com.theonetech.kotlin.BuildConfig
import com.theonetech.kotlin.data.connection.ApiClient
import com.theonetech.kotlin.data.connection.ApiInterface
import com.theonetech.kotlin.domain.utils.Utils
import junit.framework.Assert.assertEquals
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.json.JSONObject
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
@RunWith(AndroidJUnit4ClassRunner::class)
class LoginActivityTest {
    private var mMockWebServer: MockWebServer = MockWebServer()
    private var mApiService: ApiInterface? = null

    @get:Rule
    var activityLogin: ActivityTestRule<LoginActivity> = ActivityTestRule(LoginActivity::class.java)

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
        //Login API call with set valid user inputs to server
        //Get the valid server response
        val response = MockResponse()
        response.setResponseCode(HttpURLConnection.HTTP_OK)
        response.setBody(
            activityLogin.activity.application.assets.open("user_login.json").bufferedReader()
                .readLine()
        )
        mMockWebServer.enqueue(response)
        val jsonObject = JsonObject()
        jsonObject.addProperty("userName", "Student11_1A")
        jsonObject.addProperty("password", "Password12345")
        jsonObject.addProperty("schoolId", 1)
        jsonObject.addProperty("isStudent", true)
        var apiResponse: Response<JSONObject>? = null
        try {
            val call: Call<ResponseBody?>? = mApiService?.login(jsonObject)
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
            println("Login response->$response")
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
                            activityLogin.activity.application.assets.open("user_login.json")
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