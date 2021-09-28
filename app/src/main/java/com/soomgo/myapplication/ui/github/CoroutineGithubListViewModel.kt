package com.soomgo.myapplication.ui.github

import androidx.lifecycle.*
import com.soomgo.myapplication.data.model.User
import com.soomgo.myapplication.data.model.UserResponse
import com.soomgo.myapplication.data.remoteDataSource.CoroutineGithubRepository
import com.soomgo.myapplication.data.remoteDataSource.CoroutineGithubRepositoryImpl
import com.soomgo.myapplication.data.remoteDataSource.GithubRepository
import kotlinx.coroutines.launch
import retrofit2.Call

class CoroutineGithubListViewModel(private val repository: CoroutineGithubRepository) :
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


    //참고용
    val _list = MutableLiveData<UserResponse>()
    val list2 : LiveData<UserResponse>
        get() = _list

    suspend fun getUser(){
        _list.value = repository.coroutineFetchUser(_query.value?:"")
    }
}