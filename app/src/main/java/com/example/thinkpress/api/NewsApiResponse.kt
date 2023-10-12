package com.example.thinkpress.api

data class NewsApiResponse(
    val status: String,
    val totalResults: Int,
    val results: List<Article>
)

data class Article(
    val article_id: String,
    val title: String,
    val link: String,
    val keywords: List<String>?,
    val creator: List<String>?,
    val video_url: String?,
    val description: String,
    val content: String,
    val pubDate: String,
    val image_url: String?,
    val source_id: String,
    val source_priority: Int,
    val country: List<String>,
    val category: List<String>,
    val language: String
)