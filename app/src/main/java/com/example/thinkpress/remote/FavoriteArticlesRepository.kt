package com.example.thinkpress.remote

import com.example.thinkpress.api.Article
import com.google.firebase.database.FirebaseDatabase

class FavoriteArticlesRepository {

    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("favoriteArticles")

    object FavoriteArticlesRepository {
        val favoriteArticles = mutableListOf<Article>()

        fun addFavorite(article: Article) {
            favoriteArticles.add(article)
        }

        fun removeFavorite(article: Article) {
            favoriteArticles.remove(article)
        }

        fun isFavorite(article: Article): Boolean {
            return favoriteArticles.contains(article)
        }

        fun getFavorites(): List<Article> {
            return favoriteArticles
        }
    }



    fun addFavoriteArticle(articleId: String) {
        myRef.child(articleId).setValue(true)
    }


    fun removeFavoriteArticle(articleId: String) {
        myRef.child(articleId).removeValue()
    }

    // Weitere Methoden zum Abrufen von Favoriten können hier hinzugefügt werden
    companion object {
        fun addFavorite(article: Article) {
            FavoriteArticlesRepository.favoriteArticles.add(article)
        }

        fun isFavorite(article: Article): Boolean {
            return FavoriteArticlesRepository.favoriteArticles.contains(article)
        }

        fun removeFavorite(article: Article) {
            FavoriteArticlesRepository.favoriteArticles.remove(article)
        }

        fun getFavorites(): Any {
            return FavoriteArticlesRepository.favoriteArticles
        }
    }
}
