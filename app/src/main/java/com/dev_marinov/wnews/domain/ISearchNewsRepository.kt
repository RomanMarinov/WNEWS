package com.dev_marinov.wnews.domain

interface ISearchNewsRepository {
    suspend fun getSearchNews(q: String, api: String): List<News>
}