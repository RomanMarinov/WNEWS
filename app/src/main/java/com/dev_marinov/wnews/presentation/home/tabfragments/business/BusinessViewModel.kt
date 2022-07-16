package com.dev_marinov.wnews.presentation.home.tabfragments.business

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
class BusinessViewModel @Inject constructor(
    private val iCategoryNewsRepository: ICategoryNewsRepository
): ViewModel() {

    private val country = "ru"
    private val category = "business"
    private val pageSize = 100
    private val api = "f725144c0220437d87363920fe7b20ba" // help https://newsapi.org/docs

    private val _news: MutableLiveData<List<News>> = MutableLiveData()
    val news: LiveData<List<News>> = _news

    private val _uploadData = SingleLiveEvent<String>()
    val uploadData: SingleLiveEvent<String> = _uploadData

    init {
        getCategoryNews()
    }

    fun onClick(url: String){
        uploadData.postValue(url)
    }

    fun getCategoryNews(){
        viewModelScope.launch(Dispatchers.IO) {
            iCategoryNewsRepository.getCategoryNews(country, category, pageSize, api).let {
                val list: MutableList<News> = _news.value?.toMutableList()?: mutableListOf()
                list.addAll(it)
                _news.postValue(list)
            }
        }
    }




}