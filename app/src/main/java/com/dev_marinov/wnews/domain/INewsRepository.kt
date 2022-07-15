package com.dev_marinov.wnews.domain

interface INewsRepository {
    suspend fun getNews(country: String, pageSize: Int, api: String): List<News>
}