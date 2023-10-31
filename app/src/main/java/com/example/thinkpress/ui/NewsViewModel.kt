package com.example.thinkpress.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thinkpress.api.Article
import com.example.thinkpress.api.Favorite
import com.example.thinkpress.api.NewsApiResponse
import com.example.thinkpress.api.NewsApiService
import com.example.thinkpress.api.NewsResult
import com.example.thinkpress.remote.FavoriteArticlesRepository
import com.example.thinkpress.remote.NewsRepository
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    newsApiService: NewsApiService,
    private val favoriteArticlesRepository: FavoriteArticlesRepository,
    private val apiKey: String,
) : ViewModel() {

    private val newsRepository = NewsRepository(newsApiService)

    private val _newsResult = MutableLiveData<NewsResult>()
    val newsResult: LiveData<NewsResult> get() = _newsResult

    fun removeFavoriteFromDatabase(favorite: Article) {
        viewModelScope.launch {
            try {
                // Entfernen aus der Room-Datenbank
                favoriteArticlesRepository.removeFavorite(favorite)

                // Entfernen aus der Firebase-Datenbank
                val favoriteArticlesRef = FirebaseDatabase.getInstance().getReference("favoriteArticles")
                favoriteArticlesRef.child(favorite.articleId).removeValue()

            } catch (e: Exception) {
                Log.e("NewsViewModel", "Fehler beim Entfernen des Favoriten: ${e.localizedMessage}")
            }
        }
    }

    // Sucht News basierend auf einer Query
    fun searchNews(query: String) {
        viewModelScope.launch {
            try {
                val response: Response<NewsApiResponse> = newsRepository.fetchNews(apiKey, query)
                if (response.isSuccessful) {
                    _newsResult.postValue(NewsResult.Success(response.body()?.results ?: mutableListOf()))
                } else {
                    _newsResult.postValue(NewsResult.Failure(response.code(), response.message()))
                }
            } catch (e: Exception) {
                Log.e("NewsViewModel", e.localizedMessage ?: "Ein unbekannter Fehler ist aufgetreten.")
                _newsResult.postValue(NewsResult.Failure(-1, e.localizedMessage ?: "Ein unbekannter Fehler ist aufgetreten."))
            }
        }
    }

    // Ruft News mit festgelegten Suchbegriffen ab
    fun fetchNews() {
        viewModelScope.launch {
            try {
                val response: Response<NewsApiResponse> = newsRepository.fetchNews(apiKey, "Politik")
                if (response.isSuccessful) {
                    _newsResult.postValue(NewsResult.Success(response.body()?.results ?: mutableListOf()))
                    Log.i("NewsViewModel", response.body().toString())
                } else {
                    _newsResult.postValue(NewsResult.Failure(response.code(), response.message()))
                }
            } catch (e: Exception) {
                Log.e("NewsViewModel", e.localizedMessage ?: "Ein unbekannter Fehler ist aufgetreten.")
                _newsResult.postValue(NewsResult.Failure(-1, e.localizedMessage ?: "Ein unbekannter Fehler ist aufgetreten."))
            }
        }
    }

    // Überprüft, ob ein Artikel favorisiert ist
    suspend fun isFavorite(article: Article): Boolean {
        return favoriteArticlesRepository.isFavorite(article.articleId)
    }

    // Fügt einen Artikel zu den Favoriten hinzu
    fun addFavorite(article: Article) {
        viewModelScope.launch {
            favoriteArticlesRepository.addFavorite(article)
        }
    }

    // Entfernt einen Artikel aus den Favoriten
    fun removeFavorite(article: Article) {
        viewModelScope.launch {
            favoriteArticlesRepository.removeFavorite(article)
        }
    }
}
