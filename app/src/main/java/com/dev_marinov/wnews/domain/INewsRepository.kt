package com.dev_marinov.wnews.domain

import androidx.lifecycle.LiveData
import com.dev_marinov.wnews.presentation.model.SelectableFavoriteNews
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface INewsRepository {

    suspend fun getNews(): List<News>?

    suspend fun getCategoryNews(category: String) : List<News>

    suspend fun getSearchNews(q: String): List<News>

    suspend fun saveInFavorite(news: News)

    fun getNewsFavorite() : Flow<List<News>>

    suspend fun deleteNewsFavorite(news: News)
}