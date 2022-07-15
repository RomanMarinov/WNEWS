package com.dev_marinov.wnews.domain

interface ICategoryNewsRepository {
    suspend fun getCategoryNews(country: String, category: String, pageSize: Int, api: String) : List<News>
}