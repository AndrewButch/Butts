package com.andrewbutch.androiddevelopertinkofffintech2021.repository.latest

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LatestPostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<LatestPostCacheEntity>)

    @Query("SELECT * FROM latest_posts ORDER BY created_at ASC LIMIT 5 OFFSET(:page * 5)")
    fun getPostsOrderedByDateAsc(page: Int) : List<LatestPostCacheEntity>

    @Query("SELEcT * FROM latest_posts WHERE id = :postId")
    fun getPostById(postId: Int): LatestPostCacheEntity
}