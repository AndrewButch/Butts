package com.andrewbutch.androiddevelopertinkofffintech2021.repository.top

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_posts")
data class TopPostCacheEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "gif_url")
    val gifUrl: String,

    @ColumnInfo(name = "votes")
    val votes: Int
) {
}