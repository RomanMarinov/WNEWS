package com.dev_marinov.wnews.presentation.home.tabfragments.business

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_marinov.wnews.SingleLiveEvent
import com.dev_marinov.wnews.domain.INewsRepository
import com.dev_marinov.wnews.domain.News
import com.dev_marinov.wnews.presentation.home.tabfragments.allnews.AllNewsViewModel
import com.dev_marinov.wnews.presentation.model.SelectableFavoriteNews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessViewModel @Inject constructor(
    private val iNewsRepository: INewsRepository
): ViewModel() {

    private val country = "ru"
    private val category = "business"
    private val pageSize = 100
    private val api = "f725144c0220437d87363920fe7b20ba" // help https://newsapi.org/docs

    private val _uploadData = SingleLiveEvent<String>()
    val uploadData: SingleLiveEvent<String> = _uploadData

    // room
    private val favoriteNews: Flow<List<News>> = iNewsRepository.getNewsFavorite().map {
        it.sortedBy { news ->
            news.publishedAt
        }
    }

    private val _swipe: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val swipe: StateFlow<Boolean> = _swipe.asStateFlow()

    private var urlClickNews = ""

    private val _news: MutableStateFlow<List<News>> = MutableStateFlow(listOf())
    val news: Flow<List<SelectableFavoriteNews>> =
        combine(_news, favoriteNews) { news, favoriteNews ->
            news.map {
                SelectableFavoriteNews(
                    news = it,
                    isSelected = favoriteNews.contains(it)
                )
            }
        }

    init {
        getCategoryNews()
    }

    fun onSwipe(){
        _swipe.value = true
        getCategoryNews()
    }

    fun onClick(url: String) {
        uploadData.postValue(url)
    }

    // запись в бд
    fun onClickFavorite(news: SelectableFavoriteNews) {
        viewModelScope.launch(Dispatchers.IO) {

            urlClickNews = news.news.url

            if (news.isSelected) {
                iNewsRepository.deleteNewsFavorite(news = news.news)
            } else {
                iNewsRepository.saveInFavorite(news = news.news)
            }
        }
    }


    private fun getCategoryNews() {
        viewModelScope.launch(Dispatchers.IO) {
            iNewsRepository.getCategoryNews(country, category, pageSize, api)?.let {
                _news.value = it
                _swipe.value = false
            }
        }
    }


}