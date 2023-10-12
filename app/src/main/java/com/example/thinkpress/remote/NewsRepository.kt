package com.example.thinkpress.remote

import com.example.thinkpress.api.NewsApiService
import com.example.thinkpress.api.NewsApiResponse
import retrofit2.Response

class NewsRepository(private val newsApiService: NewsApiService) {
    suspend fun fetchNews(apiKey:String, query: String): Response<NewsApiResponse> {
        return newsApiService.getNews(apiKey,query)
    }
}