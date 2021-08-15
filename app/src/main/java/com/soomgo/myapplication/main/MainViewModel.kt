package com.soomgo.myapplication.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _count = MutableLiveData(0)
    val count: LiveData<Int>
        get() = _count

    fun decrease() {
        _count.value = _count.value?.minus(1)
    }

    fun increase() {
        _count.value = _count.value?.plus(1)
    }
}