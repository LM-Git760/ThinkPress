package com.example.thinkpress.api

import com.google.gson.annotations.SerializedName


data class NewsApiResponse(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("results") val results: MutableList<Article>
)

data class Article(
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
    @SerializedName("language") val language: String
)