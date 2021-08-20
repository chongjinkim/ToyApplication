package com.soomgo.myapplication.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _sharedContents = MutableLiveData<String>("empty for now")
    val sharedContents: LiveData<String>
        get() = _sharedContents


    fun setContents(contents: String) {
        _sharedContents.value = contents
    }

    fun getContents() = _sharedContents.value
}