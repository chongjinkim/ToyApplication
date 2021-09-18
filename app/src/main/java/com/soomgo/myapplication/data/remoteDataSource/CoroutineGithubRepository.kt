package com.soomgo.myapplication.data.remoteDataSource

import com.soomgo.myapplication.data.model.UserResponse
import com.soomgo.myapplication.data.network.Client
import retrofit2.Call

//클래스의 상속, 인터페이스의 구현 -> 인터페이스는 자바는 다중상속만 가능, 인터페이스는 다중 구현 가능
//행동에 대한 제한 범위를 제하는 범위가 목표, 꼭 구현을 해야 한다는 목표를 가져야함
//클래스는 다중상속 x, 인터페이스는 가능
interface CoroutineGithubRepository {
    fun fetchUser(query : String): Call<UserResponse>
    suspend fun coroutineFetchUser(query : String): UserResponse
}

//인터페이스 구현 -> 테스트 용이, 구조를 유연하게 만든다.
class CoroutineGithubRepositoryImpl(val client: Client) : CoroutineGithubRepository {
    override fun fetchUser(query: String): Call<UserResponse> {
        return client.githubApi.getUser(query)
    }

    override suspend fun coroutineFetchUser(query: String): UserResponse {
        return client.githubApi.coroutineGetUser(query)
    }
}