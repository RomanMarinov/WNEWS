package com.dev_marinov.wnews.data

import com.dev_marinov.wnews.data.remote.NewsService
import com.dev_marinov.wnews.domain.ICategoryNewsRepository
import com.dev_marinov.wnews.domain.News
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryNewsRepository @Inject constructor(private val newsService: NewsService)
    : ICategoryNewsRepository {

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

}