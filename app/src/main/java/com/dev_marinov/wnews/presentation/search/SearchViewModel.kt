package com.dev_marinov.wnews.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.wnews.SingleLiveEvent
import com.dev_marinov.wnews.domain.ISearchNewsRepository
import com.dev_marinov.wnews.domain.News
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val iSearchNewsRepository: ISearchNewsRepository
) : ViewModel() {
    private val api = "f725144c0220437d87363920fe7b20ba" // help https://newsapi.org/docs

    private val _searchNews: MutableLiveData<List<News>> = MutableLiveData()
    val searchNews: LiveData<List<News>> = _searchNews

    private val _uploadData = SingleLiveEvent<String>()
    val uploadData: SingleLiveEvent<String> = _uploadData

    fun onClick(url: String){
        _uploadData.postValue(url)
    }


    fun getSearchNews(q: String){
        viewModelScope.launch(Dispatchers.IO) {
            iSearchNewsRepository.getSearchNews(q, api).let {
                val list: MutableList<News> = _searchNews.value?.toMutableList()?: mutableListOf()
                list.addAll(it)
                _searchNews.postValue(list)
            }
        }
    }
}