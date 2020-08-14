package com.theonetech.kotlin.data.connection
import com.theonetech.kotlin.BuildConfig
import com.theonetech.kotlin.domain.utils.Utils
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Mahesh Keshvala on 28,July,2020
 */
object ApiClient {
    //class for retrofit initialization
    private const val TIME = 2
    private var retrofit: Retrofit? = null
    private val httpClientOther: OkHttpClient = provideOkhttpClient()

    @Provides
    @Singleton
    internal fun provideOkhttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(TIME.toLong(), TimeUnit.SECONDS)
        httpClient.readTimeout(TIME.toLong(), TimeUnit.SECONDS)
        return httpClient.build()
    }

    fun getClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(httpClientOther)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
        }
        return retrofit
    }
}