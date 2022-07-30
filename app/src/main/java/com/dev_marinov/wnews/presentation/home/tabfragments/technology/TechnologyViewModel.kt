package com.dev_marinov.wnews.presentation.home.tabfragments.technology

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class TechnologyViewModel @Inject constructor(
    private val iNewsRepository: INewsRepository
): ViewModel() {

    private val country = "ru"
    private val category = "technology"
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

//    private val _event = SingleLiveEvent<Event>()
//    val event: SingleLiveEvent<Event> = _event

    private val _swipe: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val swipe: StateFlow<Boolean> = _swipe.asStateFlow()

    private var urlClickNews = ""

    private val _news: MutableStateFlow<List<News>> = MutableStateFlow(listOf())
    val news: Flow<List<SelectableFavoriteNews>> =
        combine(_news, favoriteNews) { news, favoriteNews ->
            news.map {
//                if (urlClickNews == it.url) {
//                    val isSelected = favoriteNews.contains(it)
//                    checkIsSelected(isSelected)
//                }
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

//    private fun checkIsSelected(isSelected: Boolean) {
//        if (isSelected) {
//            event.postValue(Event.SaveFavorite())
//        } else {
//            event.postValue(Event.DeleteFavorite())
//        }
//    }

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

//                _event.postValue(Event.Success())
            }
//                ?: run{_event.postValue(Event.Failure())}
        }
    }

//    sealed class Event(val message: String){
//        class Failure : Event("ошибка сетевого запроса")
//        class Success : Event("данные обновлены")
//        class SaveFavorite : Event("новость добалена в избранное")
//        class DeleteFavorite : Event("новость удалена из избранное")
//    }

}