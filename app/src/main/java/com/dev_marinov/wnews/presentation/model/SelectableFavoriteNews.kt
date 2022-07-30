package com.dev_marinov.wnews.presentation.model

import com.dev_marinov.wnews.domain.News

data class SelectableFavoriteNews(
    val news: News,
    val isSelected: Boolean
)