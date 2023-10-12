package com.example.thinkpress.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thinkpress.api.NewsApiService

class NewsViewModelFactory(
    private val newsApiService: NewsApiService,
    private val apiKey: String,
    private val query: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(newsApiService, apiKey, query) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}