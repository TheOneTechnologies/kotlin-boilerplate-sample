package com.theonetech.kotlin.presentation.viewmodel

import com.google.gson.Gson
import com.theonetech.kotlin.data.connection.ApiClient
import com.theonetech.kotlin.domain.utils.MockResponseFileReader
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/*
@RunWith(JUnit4::class)
class MockWebUnitTests {
    private val server: MockWebServer = MockWebServer()
    private val MOCK_WEBSERVER_PORT = 8000
    lateinit var placeholderApi: ApiClient
    lateinit var jsonRepository: JsonRepository

    @Before
    fun init() {
        server.start(MOCK_WEBSERVER_PORT)
        placeholderApi = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(ApiClient::class.java)
        jsonRepository = JsonRepository(placeholderApi)
    }

    @After
    fun shutdown() {
        server.shutdown()
    }

    @Test
    fun testSuccessfulResponse() {
        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(200)
                    .setBody(MockResponseFileReader("user_login.json").content))
            }
        }
        activityRule.launchActivity(null)
    }


    @Test
    fun `JsonPlaceholder APIs parse correctly`() {
        server.apply {
            enqueue(MockResponse().setBody(MockResponseFileReader("user_login.json").content))
        }
        jsonRepository.observePosts()
            .test()
            .awaitDone(3, TimeUnit.SECONDS)
            .assertComplete()
            .assertValueCount(1)
            .assertValue { it.size == 2 }
            .assertNoErrors()
    }
}*/
