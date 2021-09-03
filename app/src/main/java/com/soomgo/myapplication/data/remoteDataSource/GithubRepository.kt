package com.soomgo.myapplication.data.remoteDataSource

import androidx.lifecycle.LiveData
import com.soomgo.myapplication.data.model.User
import com.soomgo.myapplication.data.network.Client
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface GithubRepository {
    fun fetchUser(query : String): List<User>
}

class GithubRepositoryImpl(val client: Client) : GithubRepository {
    override fun fetchUser(query : String): List<User> {
        var result :List<User> = emptyList()
        val asdfasdf: Call<List<User>> = client.githubApi.getUser(query)

        asdfasdf.enqueue(object : Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                result = response.body()?: emptyList()
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
//                TODO("Not yet implemented")
            }

        })
        return result
    }
}