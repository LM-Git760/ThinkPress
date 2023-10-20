package com.example.thinkpress.remote

import com.example.thinkpress.api.NewsApiService
import com.example.thinkpress.api.NewsApiResponse
import retrofit2.Response

// Klasse zur Verwaltung der Kommunikation mit dem News-API-Dienst.
class NewsRepository(private val newsApiService: NewsApiService) {
    // Methode zum Abrufen von Nachrichten vom API-Dienst.
    suspend fun fetchNews(apiKey:String, query: String): Response<NewsApiResponse> {
        return newsApiService.getNews(apiKey,query)
    }
}
