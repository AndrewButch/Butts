package com.andrewbutch.androiddevelopertinkofffintech2021.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andrewbutch.androiddevelopertinkofffintech2021.repository.latest.LatestPostCacheEntity
import com.andrewbutch.androiddevelopertinkofffintech2021.repository.latest.LatestPostDao
import com.andrewbutch.androiddevelopertinkofffintech2021.repository.top.TopPostCacheEntity
import com.andrewbutch.androiddevelopertinkofffintech2021.repository.top.TopPostDao

@Database(
    entities = [LatestPostCacheEntity::class, TopPostCacheEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PostsDatabase : RoomDatabase() {

    abstract fun latestPostsDao(): LatestPostDao
    abstract fun topPostsDao(): TopPostDao

    companion object {
        private var INSTANCE: PostsDatabase? = null
        private const val DB_NAME = "latest_posts_db"

        fun getDatabase(context: Context): PostsDatabase {
            return INSTANCE ?: synchronized(this) {
                val database = Room
                    .databaseBuilder(context, PostsDatabase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = database
                database
            }
        }
    }
}