package com.soomgo.myapplication.data.remoteDataSource

import com.soomgo.myapplication.data.model.UserResponse
import com.soomgo.myapplication.data.network.Client
import retrofit2.Call

interface GithubRepository {
    fun fetchUser(query : String): Call<UserResponse>
}

class GithubRepositoryImpl(val client: Client) : GithubRepository {
    override fun fetchUser(query: String): Call<UserResponse> {
        return client.githubApi.getUser(query)
    }
}