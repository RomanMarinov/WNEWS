package com.dev_marinov.wnews.presentation.home.tabfragments.allnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.wnews.SingleLiveEvent
import com.dev_marinov.wnews.domain.INewsRepository
import com.dev_marinov.wnews.domain.News
import com.dev_marinov.wnews.domain.ISearchNewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllNewsViewModel @Inject constructor(
    private val iNewsRepository: INewsRepository,
    ) : ViewModel() {

    private val api = "f725144c0220437d87363920fe7b20ba" // help https://newsapi.org/docs
    private var country = "ru"
    private val pageSize = 100

    private val _homeNews: MutableLiveData<List<News>> = MutableLiveData()
    val homeNews: LiveData<List<News>> = _homeNews

    private val _uploadData = SingleLiveEvent<String>()
    val uploadData: SingleLiveEvent<String> = _uploadData


    init {
        getHomeNews()
    }


    fun onClick(url: String){
        uploadData.postValue(url)
    }

    private fun getHomeNews(){
        viewModelScope.launch(Dispatchers.IO) {
            iNewsRepository.getNews(country, pageSize, api).let {
                val list: MutableList<News> = _homeNews.value?.toMutableList()?: mutableListOf()
                list.addAll(it)
                _homeNews.postValue(list)
            }
        }
    }




}