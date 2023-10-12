package com.example.thinkpress.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thinkpress.api.NewsApiService
import com.example.thinkpress.api.NewsApiResponse
import com.example.thinkpress.api.NewsResult
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    private val newsApiService: NewsApiService,
    private val apiKey: String,
) : ViewModel() {

    private val _newsResult = MutableLiveData<NewsResult>()
    val newsResult: LiveData<NewsResult> get() = _newsResult

    fun searchNews(query: String) {
        viewModelScope.launch {
            try {
                val response: Response<NewsApiResponse> = newsApiService.getNews(apiKey, query)
                if (response.isSuccessful) {
                    _newsResult.postValue(NewsResult.Success(response.body()?.results ?: mutableListOf()))
                } else {
                    _newsResult.postValue(NewsResult.Failure(response.code(), response.message()))
                }
            } catch (e: Exception) {
                _newsResult.postValue(NewsResult.Failure(-1, e.localizedMessage ?: "Ein unbekannter Fehler ist aufgetreten."))
            }
        }
    }

    fun fetchNews() {
        viewModelScope.launch {
            try {
                val response: Response<NewsApiResponse> = newsApiService.getNews("pub_310178ef71a1b033f97594bf39bee90edfc10" ,"Krieg, Gaza,Israel,Bundeswehr")
                if (response.isSuccessful) {
                    _newsResult.postValue(NewsResult.Success(response.body()?.results ?: mutableListOf()))

                    Log.i("NewsAdapter", response.body().toString())
                } else {
                    _newsResult.postValue(NewsResult.Failure(response.code(), response.message()))
                }
            } catch (e: Exception) {
                _newsResult.postValue(NewsResult.Failure(-1, e.localizedMessage ?: "Ein unbekannter Fehler ist aufgetreten."))
            }
        }
    }
}
