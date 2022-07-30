package com.dev_marinov.wnews.data.remote

import com.dev_marinov.wnews.data.remote.dto.ArticlesDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private val defaultCountry: String = "ru"
private val defaultApiKey = "f725144c0220437d87363920fe7b20ba"
private val defaultpageSize: Int = 100

interface NewsService {

    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String = defaultCountry,
        @Query("pageSize") pageSize: Int = defaultpageSize,
        @Query("apiKey") apiKey: String = defaultApiKey
    ): Response<ArticlesDTO>

    @GET("top-headlines")
    suspend fun getCategoryNews(
        @Query("country") country: String = defaultCountry,
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int = defaultpageSize,
        @Query("apiKey") apiKey: String = defaultApiKey
    ): Response<ArticlesDTO>

    @GET("everything")
    suspend fun getSearchNews(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String = defaultApiKey
    ): Response<ArticlesDTO>

}