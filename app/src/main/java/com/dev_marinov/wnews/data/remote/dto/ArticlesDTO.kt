package com.dev_marinov.wnews.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ArticlesDTO(
    @SerializedName("articles")
    val articles: List<NewsDTO>
)
