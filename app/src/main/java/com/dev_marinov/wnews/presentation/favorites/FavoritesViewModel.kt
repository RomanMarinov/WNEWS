package com.dev_marinov.wnews.presentation.favorites

import androidx.lifecycle.*
import com.dev_marinov.wnews.SingleLiveEvent
import com.dev_marinov.wnews.domain.INewsRepository
import com.dev_marinov.wnews.domain.News
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val newsRepository: INewsRepository
) : ViewModel() {

    val news: Flow<List<News>> = newsRepository.getNewsFavorite().map {
        it.sortedBy { news ->
            news.publishedAt
        }
    }

    private val _uploadData = SingleLiveEvent<String>()
    val uploadData: SingleLiveEvent<String> = _uploadData

    fun onClick(url: String) {
        uploadData.postValue(url)
    }

    fun onClickFavorite(news: News) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.deleteNewsFavorite(news = news)
        }
    }

}