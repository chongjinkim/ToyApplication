package com.soomgo.myapplication.data.remoteDataSource

import com.soomgo.myapplication.data.model.UserResponse
import com.soomgo.myapplication.data.network.Client
import retrofit2.Call

interface CoroutineGithubRepository {
    fun fetchUser(query : String): Call<UserResponse>
    suspend fun coroutineFetchUser(query : String): UserResponse
}

class CoroutineGithubRepositoryImpl(val client: Client) : CoroutineGithubRepository {
    override fun fetchUser(query: String): Call<UserResponse> {
        return client.githubApi.getUser(query)
    }

    override suspend fun coroutineFetchUser(query: String): UserResponse {
        return client.githubApi.coroutineGetUser(query)
    }
}