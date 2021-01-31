package com.andrewbutch.androiddevelopertinkofffintech2021.repository.latest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "latest_posts")
data class LatestPostCacheEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "gif_url")
    val gifUrl: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Long
)
