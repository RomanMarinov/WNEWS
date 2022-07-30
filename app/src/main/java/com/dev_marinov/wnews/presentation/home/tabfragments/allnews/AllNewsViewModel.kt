package com.dev_marinov.wnews.presentation.home.tabfragments.allnews

import androidx.lifecycle.*
import com.dev_marinov.wnews.SingleLiveEvent
import com.dev_marinov.wnews.domain.INewsRepository
import com.dev_marinov.wnews.domain.News
import com.dev_marinov.wnews.presentation.model.SelectableFavoriteNews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllNewsViewModel @Inject constructor(
    private val newsRepository: INewsRepository,
) : ViewModel() {

    private val _uploadData = SingleLiveEvent<String>()
    val uploadData: SingleLiveEvent<String> = _uploadData

    private val favoriteNews: Flow<List<News>> = newsRepository.getNewsFavorite().map {
        it.sortedBy { news ->
            news.publishedAt
        }
    }

    private val _isLoaderVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val swipe: StateFlow<Boolean> = _isLoaderVisible.asStateFlow()

    private val _news: MutableStateFlow<List<News>> = MutableStateFlow(listOf())

    private var urlClickNews = ""

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
        getHomeNews()
    }

    fun onSwipe() {
        _isLoaderVisible.value = true
        getHomeNews()
    }

    fun onClick(url: String) {
        uploadData.postValue(url)
    }

    fun onClickFavorite(news: SelectableFavoriteNews) {
        viewModelScope.launch(Dispatchers.IO) {

            urlClickNews = news.news.url

            if (news.isSelected) {
                newsRepository.deleteNewsFavorite(news = news.news)
            } else {
                newsRepository.saveInFavorite(news = news.news)
            }
        }
    }

    private fun getHomeNews() {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.getNews()?.let {
                _news.value = it
                _isLoaderVisible.value = false
            }
        }

    }
}