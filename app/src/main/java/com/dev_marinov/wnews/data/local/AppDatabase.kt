package com.dev_marinov.wnews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NewsEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}