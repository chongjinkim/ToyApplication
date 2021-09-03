package com.soomgo.myapplication.ui.github

import androidx.lifecycle.*
import com.soomgo.myapplication.data.model.User
import com.soomgo.myapplication.data.model.UserResponse
import com.soomgo.myapplication.data.remoteDataSource.CoroutineGithubRepository
import com.soomgo.myapplication.data.remoteDataSource.CoroutineGithubRepositoryImpl
import com.soomgo.myapplication.data.remoteDataSource.GithubRepository
import retrofit2.Call

class CoroutineGithubViewModel(private val repository: CoroutineGithubRepository) :
    ViewModel() {
    private val _query = MutableLiveData("")

    val list: LiveData<UserResponse> = Transformations.switchMap(_query) {
        if (!it.isNullOrEmpty()) {
            liveData {
                emit(repository.coroutineFetchUser(it))
            }
        } else {
            liveData { emit(UserResponse()) }
        }
    }

    fun setQuery(query: String) {
        _query.value = query
    }

    suspend fun getUsers(query: String): UserResponse {
        return repository.coroutineFetchUser(query)
    }
}