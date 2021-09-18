package com.soomgo.myapplication.ui.github.testview

import androidx.lifecycle.*
import com.soomgo.myapplication.data.model.User
import com.soomgo.myapplication.data.model.UserResponse
import com.soomgo.myapplication.data.testrepository.TestCoroutineGithubRepository
import com.soomgo.myapplication.data.testrepository.TestCoroutineGithubRepositoryImpl
import kotlinx.coroutines.launch

class TestCoroutineViewModel(val repository: TestCoroutineGithubRepository) : ViewModel() {

    private val _user = MutableLiveData<List<User>>()
    private val _q = MutableLiveData<String>()

    val user: LiveData<List<User>>
        get() = _user

    suspend fun getUser(query: String) {
        _user.value = repository.fetchUser(query).items
    }

}

