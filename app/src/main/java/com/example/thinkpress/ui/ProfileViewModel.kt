package com.example.thinkpress.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thinkpress.api.Article
import com.example.thinkpress.remote.FavoriteArticlesRepository

class ProfileViewModel(favoriteArticlesRepository: FavoriteArticlesRepository) : ViewModel() {
    val favoriteArticles: LiveData<List<Article>> = favoriteArticlesRepository.getFavoriteArticles().also {
        it.observeForever { articles ->
            Log.d("ProfileViewModel", "Received articles: $articles")
        }
    }
    class ProfileViewModelFactory(private val favoriteArticlesRepository: FavoriteArticlesRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
                return ProfileViewModel(favoriteArticlesRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
