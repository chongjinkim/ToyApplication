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

//    @GET("search/users")
//    fun getUser(
//        @Query("q") q: String?
//    ): Single<UserResponse>

    @GET("search/users")
    fun getUser(
        @Query("q") q: String?
    ): Call<List<User>>

//    @GET("search/users")
//    suspend fun getUserCoroutines(
//        @Query("q") q: String?,
//        @Query("per_page") page : Int = 3
//
//    ): UserResponse
//
//    @GET("search/users")
//    suspend fun getUserCoroutinesPaging(
//        @Query("q") q: String?,
//        @Query("page") p : Int,
//        @Query("per_page") page : Int = 3
//    ): UserResponse
//
//    @GET("search/users")
//    suspend fun getUserCoroutinesResult(
//        @Query("q") q: String?
//    ): Response<UserResponse>
}