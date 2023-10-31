package com.example.thinkpress.remote

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.thinkpress.api.Article
import com.example.thinkpress.api.Favorite
import com.example.thinkpress.db.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Klasse zur Verwaltung der Datenzugriffsmethoden für favorisierte Artikel.
class FavoriteArticlesRepository(context: Context) {

    // Singleton-Instanz der AppDatabase.
    private val db = AppDatabase.getInstance(context)
    // DAO-Objekt zur Interaktion mit der Datenbank.
    private val articleDao = db.articleDao()
    // Koroutinen-Scope zur Verwaltung asynchroner Aufgaben.
    private val repoScope = CoroutineScope(Dispatchers.IO)

    // Methode zum Hinzufügen eines Artikels zu den Favoriten.
    fun addFavorite(article: Article) {
        // Start einer Koroutine zur Ausführung der asynchronen Operation.
        repoScope.launch {
            articleDao.insert(article)
        }
    }
    fun getFavoriteArticles(): LiveData<List<Favorite>> {
        return articleDao.getFavoriteArticles()
    }

    // Methode zum Entfernen eines Artikels aus den Favoriten.
    fun removeFavorite(article: Article) {
        repoScope.launch {
            articleDao.delete(article)
        }
    }

    // Methode zum Überprüfen, ob ein Artikel als Favorit markiert ist.
    suspend fun isFavorite(id: String): Boolean {
        return articleDao.isFavorite(id) != null
    }

}
