package com.soomgo.myapplication.data.network

import com.google.gson.Gson
import com.soomgo.myapplication.BuildConfig
import com.soomgo.myapplication.data.remoteDataSource.GithubApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Client(private val gSon: Gson) {
    var client: Retrofit = createClient()
    var githubApi: GithubApi = createClient(USER_BASE_URL).create(GithubApi::class.java)

    private val httpLogLevel
        get() = if (BuildConfig.DEBUG) HttpCustomLoggingInterceptor.Level.BODY else HttpCustomLoggingInterceptor.Level.NONE

    private fun createClient(host: String = USER_BASE_URL): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpCustomLoggingInterceptor().apply { level = httpLogLevel })
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gSon))
            .client(okHttpClient)
            .baseUrl(host)
            .build()
    }

    companion object {
        private const val CONNECT_TIMEOUT = 10L
        private const val WRITE_TIMEOUT = 10L
        private const val READ_TIMEOUT = 10L

        private const val USER_BASE_URL = "https://api.github.com/"
    }
}

