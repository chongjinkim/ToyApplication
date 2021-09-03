package com.soomgo.myapplication.data.remoteDataSource

import androidx.lifecycle.LiveData
import com.soomgo.myapplication.data.model.User
import com.soomgo.myapplication.data.model.UserResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GithubApi {
    @GET("search/users")
    fun getUser(
        @Query("q") q: String?
    ): Call<UserResponse>

    @GET("search/users")
    suspend fun coroutineGetUser(
        @Query("q") q: String?,
    ): UserResponse
}