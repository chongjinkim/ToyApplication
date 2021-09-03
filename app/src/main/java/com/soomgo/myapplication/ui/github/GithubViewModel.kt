package com.soomgo.myapplication.ui.github

import androidx.lifecycle.ViewModel
import com.soomgo.myapplication.data.model.User
import com.soomgo.myapplication.data.model.UserResponse
import com.soomgo.myapplication.data.remoteDataSource.GithubRepository
import retrofit2.Call

class GithubViewModel(private val repository: GithubRepository) : ViewModel() {
    fun getUsers(query : String): Call<UserResponse> {
        return repository.fetchUser(query)
    }
}