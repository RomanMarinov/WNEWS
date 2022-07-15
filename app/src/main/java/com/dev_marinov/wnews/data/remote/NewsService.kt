package com.dev_marinov.wnews.data.remote

import com.dev_marinov.wnews.data.remote.dto.ArticlesDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String
    ): Response<ArticlesDTO>

    @GET("top-headlines")
    suspend fun getCategoryNews(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String
    ): Response<ArticlesDTO>

    @GET("everything")
    suspend fun getSearchNews(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String
    ): Response<ArticlesDTO>

}