package com.dev_marinov.wnews.data

import com.dev_marinov.wnews.data.remote.NewsService
import com.dev_marinov.wnews.domain.News
import com.dev_marinov.wnews.domain.ISearchNewsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchNewsRepository @Inject constructor(private val newsService: NewsService)
    : ISearchNewsRepository {

    override suspend fun getSearchNews(q: String, api: String): List<News> {
        val response = newsService.getSearchNews(q = q, apiKey = api)
        val news = response.body()?.let {
            it.articles.map { news ->
                news.mapToDomain()
            }
        } ?: listOf()
        return news

    }
}