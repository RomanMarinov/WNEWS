package com.dev_marinov.wnews.data

import android.util.Log
import com.dev_marinov.wnews.data.remote.NewsService
import com.dev_marinov.wnews.domain.INewsRepository
import com.dev_marinov.wnews.domain.News
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val newsService: NewsService) : INewsRepository {

    override suspend fun getNews(country: String, pageSize: Int, api: String): List<News> {
        val response = newsService.getNews(country = country, pageSize = pageSize, apiKey = api)
        val news = response.body()?.let {
            it.articles.map { news ->
                news.mapToDomain()
            }
        } ?: listOf()
        return news
    }

}