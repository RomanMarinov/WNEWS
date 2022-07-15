package com.dev_marinov.wnews.data.remote.dto

import com.dev_marinov.wnews.domain.News
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class NewsDTO(

    @SerializedName("author")
    val author: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("content")
    val content: String?,
) {
    fun mapToDomain() : News {
        return News(
            author = author,
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt,
            content = content
        )
    }
}
