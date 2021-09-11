package com.soomgo.myapplication.ui.github

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soomgo.myapplication.data.model.UserResponse
import com.soomgo.myapplication.data.remoteDataSource.CoroutineGithubRepository
import com.soomgo.myapplication.data.remoteDataSource.CoroutineGithubRepositoryImpl

class TestViewModel(val repository: CoroutineGithubRepositoryImpl) : ViewModel() {
    val _list = MutableLiveData<UserResponse>()
    val list : LiveData<UserResponse>
        get() = _list
    suspend fun getUser(){
        _list.value = repository.client.githubApi.coroutineGetUser("TEST")
    }
}