package com.dev_marinov.wnews.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.wnews.SingleLiveEvent
import com.dev_marinov.wnews.domain.ICategoryNewsRepository
import com.dev_marinov.wnews.domain.News
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(): ViewModel() {

    private val _news: MutableLiveData<List<News>> = MutableLiveData()
    val news: LiveData<List<News>> = _news

    private val _uploadData = SingleLiveEvent<String>()
    val uploadData: SingleLiveEvent<String> = _uploadData

    init {

    }

    fun onClick(url: String){
        uploadData.postValue(url)
    }







}