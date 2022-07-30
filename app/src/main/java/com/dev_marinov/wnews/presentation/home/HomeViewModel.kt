package com.dev_marinov.wnews.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    //var lastTab = 0

    private val _lastTab: MutableLiveData<Int> = MutableLiveData()
    val lastTab: LiveData<Int> = _lastTab


    fun onSelectTabPosition(position: Int) {
        _lastTab.value = position
    }





}