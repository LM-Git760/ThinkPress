package com.example.thinkpress.api

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class NewsApiResponse(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("results") val results: MutableList<Article>
)
@Entity
data class Article(
    @PrimaryKey
    @SerializedName("article_id") val articleId: String,
    @SerializedName("title") val title: String,
    @SerializedName("link") val link: String,
    @SerializedName("keywords") val keywords: List<String>?,
    @SerializedName("creator") val creator: List<String>,
    @SerializedName("video_url") val videoUrl: String?,
    @SerializedName("description") val description: String,
    @SerializedName("content") val content: String,
    @SerializedName("pubDate") val pubDate: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("source_id") val sourceId: String,
    @SerializedName("source_priority") val sourcePriority: Int,
    @SerializedName("country") val country: List<String>,
    @SerializedName("category") val category: List<String>,
    @SerializedName("language") val language: String,
    //Changelog
    @SerializedName("prioritydomain") val priorityDomain: String?,
    @SerializedName("timeframe") val timeframe: String?,
    @SerializedName("excludedomain") val excludeDomain: String?,
    @SerializedName("timezone") val timezone: String?,
    @SerializedName("full_content") val fullContent: Boolean?,
    @SerializedName("image") val image: Boolean?,
    @SerializedName("video") val video: Boolean?
)