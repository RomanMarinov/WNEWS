package com.dev_marinov.wnews.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.dev_marinov.wnews.data.local.NewsDao
import com.dev_marinov.wnews.data.local.NewsEntity
import com.dev_marinov.wnews.data.remote.NewsService
import com.dev_marinov.wnews.domain.INewsRepository
import com.dev_marinov.wnews.domain.News
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val newsDao: NewsDao,
    private val newsService: NewsService) : INewsRepository {

    override suspend fun getNews(country: String, pageSize: Int, api: String): List<News>? {
        val response = newsService.getNews(country = country, pageSize = pageSize, apiKey = api)
        val news = response.body()?.let {
            it.articles.map { news ->
                news.mapToDomain()
            }
        }
        return news
    }

    override suspend fun getCategoryNews(country: String, category: String, pageSize: Int, api: String
    ): List<News> {
        val response = newsService.getCategoryNews(country = country, category = category, pageSize = pageSize, apiKey = api)
        val news = response.body()?.let {
            it.articles.map { news ->
                news.mapToDomain()
            }
        } ?: listOf()
        return news
    }

    override suspend fun getSearchNews(q: String, api: String): List<News> {
        val response = newsService.getSearchNews(q = q, apiKey = api)
        val news = response.body()?.let {
            it.articles.map { news ->
                news.mapToDomain()
            }
        } ?: listOf()
        return news
    }

    override suspend fun saveInFavorite(news: News) {
        newsDao.insert(news = NewsEntity.mapFromDomainToData(news))
    }

    override fun getNewsFavorite() : Flow<List<News>> {
        val result = newsDao.getAll()
        return result.map {
            it.map { news ->
                news.mapToDomain()
            }
        }
    }

    override suspend fun deleteNewsFavorite(news: News) {
        newsDao.delete(news = NewsEntity.mapFromDomainToData(news))
    }
}