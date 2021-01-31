package com.andrewbutch.androiddevelopertinkofffintech2021.repository.top

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TopPostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<TopPostCacheEntity>)

    @Query("SELECT * FROM top_posts ORDER BY votes DESC LIMIT 5 OFFSET(:page * 5)")
    fun getPostsOrderedByVotesDesc(page: Int): List<TopPostCacheEntity>

    @Query("SELECT * FROM top_posts WHERE id = :postId")
    fun getPostById(postId: Int): TopPostCacheEntity
}