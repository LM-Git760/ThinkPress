package com.example.thinkpress.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thinkpress.api.NewsApiService
import com.example.thinkpress.remote.FavoriteArticlesRepository

class NewsViewModelFactory(
    private val newsApiService: NewsApiService,
    private val favoriteArticlesRepository: FavoriteArticlesRepository,
    private val apiKey: String,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(newsApiService, favoriteArticlesRepository, apiKey) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
