package com.soomgo.myapplication.ui.github

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soomgo.myapplication.data.model.User
import com.soomgo.myapplication.data.remoteDataSource.GithubRepository
import com.soomgo.myapplication.data.remoteDataSource.GithubRepositoryImpl

class GithubViewModel(private val repository: GithubRepository) : ViewModel() {
    fun getUsers(query : String): List<User> {
        return repository.fetchUser(query)
    }
}