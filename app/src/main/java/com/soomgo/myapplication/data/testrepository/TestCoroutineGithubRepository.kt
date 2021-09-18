package com.soomgo.myapplication.data.testrepository

import com.soomgo.myapplication.data.model.UserResponse
import com.soomgo.myapplication.data.network.Client
import okhttp3.OkHttpClient

interface TestCoroutineGithubRepository {

    suspend fun fetchUser(query : String) : UserResponse

}

class TestCoroutineGithubRepositoryImpl(val client: Client) : TestCoroutineGithubRepository{
    override suspend fun fetchUser(query: String): UserResponse {
        return client.githubApi.coroutineGetUser(query)
    }

}