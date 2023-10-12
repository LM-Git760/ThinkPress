package com.example.thinkpress.remote

import com.example.thinkpress.api.NewsApiService
import com.example.thinkpress.api.NewsResponse
import retrofit2.Response

class NewsRepository(private val newsApiService: NewsApiService) {
    suspend fun fetchNews(query: String, lang: String): Response<NewsResponse> {
        return newsApiService.getNews(query, lang)
    }
}