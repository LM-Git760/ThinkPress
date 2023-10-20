package com.example.thinkpress.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thinkpress.api.NewsApiService
import com.example.thinkpress.remote.FavoriteArticlesRepository

// Definiere eine Factory-Klasse zur Erzeugung von NewsViewModel-Instanzen
class NewsViewModelFactory(
    private val newsApiService: NewsApiService,  // Referenz auf den NewsApiService
    private val favoriteArticlesRepository: FavoriteArticlesRepository,  // Referenz auf das FavoriteArticlesRepository
    private val apiKey: String,  // API-Schlüssel für den NewsApiService
) : ViewModelProvider.Factory {

    // Überschreibe die create-Methode von ViewModelProvider.Factory
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Prüfe, ob die modelClass-Variable NewsViewModel::class.java zugeordnet werden kann
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            // Wenn ja, erzeuge eine Instanz von NewsViewModel und gib sie zurück
            return NewsViewModel(newsApiService, favoriteArticlesRepository, apiKey) as T
        }
        // Wenn nicht, wirf eine IllegalArgumentException
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
