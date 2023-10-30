package com.example.thinkpress.api

import androidx.room.Entity

@Entity(tableName = "favorite-articles")
data class Favorite(
    val articleId: String?,
    val title: String?,
    val descriptor: String?
)
