package com.example.thinkpress.remote

import com.example.thinkpress.api.NewsApiService
import com.example.thinkpress.api.NewsResponse

class NewsRepository(private val newsApiService: NewsApiService) {
    suspend fun fetchNews(query: String, lang: String): NewsResponse {
        return newsApiService.getNews(query, lang)
    }
}