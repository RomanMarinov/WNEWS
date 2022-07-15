package com.dev_marinov.wnews.di

import com.dev_marinov.wnews.data.CategoryNewsRepository
import com.dev_marinov.wnews.data.NewsRepository
import com.dev_marinov.wnews.data.SearchNewsRepository
import com.dev_marinov.wnews.domain.ICategoryNewsRepository
import com.dev_marinov.wnews.domain.INewsRepository
import com.dev_marinov.wnews.domain.ISearchNewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNewsRepository(newsRepository: NewsRepository)
    : INewsRepository

    @Binds
    @Singleton
    abstract fun bindCategoryNewsRepository(categoryNewsRepository: CategoryNewsRepository)
    : ICategoryNewsRepository

    @Binds
    @Singleton
    abstract fun bindSearchNewsRepository(searchNewsRepository: SearchNewsRepository)
    : ISearchNewsRepository
}

