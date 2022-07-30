package com.dev_marinov.wnews.di

import android.content.Context
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import com.dev_marinov.wnews.data.local.AppDatabase
import com.dev_marinov.wnews.data.local.NewsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Provides
    @Singleton
    fun provideNewsDao(appDatabase: AppDatabase) : NewsDao {
        return appDatabase.newsDao()
    }

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context) : AppDatabase {
        val db = databaseBuilder(
            context,
            AppDatabase::class.java, "database-news"
        ).fallbackToDestructiveMigration().build()
        return db
    }

}