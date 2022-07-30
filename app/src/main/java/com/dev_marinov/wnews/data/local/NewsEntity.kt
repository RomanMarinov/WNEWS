package com.dev_marinov.wnews.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev_marinov.wnews.domain.News
import java.util.*
import kotlin.math.roundToInt

@Entity (tableName = "news")
data class NewsEntity(
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "description") val description: String?,
    @PrimaryKey
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "urlToImage") val urlToImage: String?,
    @ColumnInfo(name = "publishedAt") val publishedAt: String?
    ) {

    companion object {
        fun mapFromDomainToData(news: News) : NewsEntity {
            return NewsEntity(
                author = news.author,
                title = news.title,
                description = news.description,
                url = news.url,
                urlToImage = news.urlToImage,
                publishedAt = news.publishedAt
            )
        }
    }

    fun mapToDomain() : News {
        return News(
            author = author,
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt
        )
    }
}

