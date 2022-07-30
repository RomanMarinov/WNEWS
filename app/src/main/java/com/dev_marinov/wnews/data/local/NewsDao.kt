package com.dev_marinov.wnews.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dev_marinov.wnews.domain.News
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getAll(): Flow<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: NewsEntity)

    @Delete
    fun delete(news: NewsEntity)
}