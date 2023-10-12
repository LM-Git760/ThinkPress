package com.example.thinkpress.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thinkpress.api.NewsApiService
import com.example.thinkpress.api.NewsResponse
import kotlinx.coroutines.launch

class NewsViewModel(private val newsApiService: NewsApiService)  : ViewModel() {

    private val _newsResponse = MutableLiveData<NewsResponse>()
    val newsResponse: LiveData<NewsResponse> get() = _newsResponse


    fun fetchNews(query: String, lang: String) {
        viewModelScope.launch {
            try {
                val response = newsApiService.getNews(query, lang)
                _newsResponse.postValue(response)
            } catch (e: Exception) {
                // Hier die Antwort verarbeiten
            }
        }
    }
}