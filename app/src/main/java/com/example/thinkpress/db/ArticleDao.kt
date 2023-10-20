package com.example.thinkpress.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import com.example.thinkpress.api.Article

// Definition des DAO (Data Access Object) zur Interaktion mit der Datenbank.
@Dao
interface ArticleDao {

    // Methode zum Einfügen eines Artikels in die Datenbank.
    @Insert
    suspend fun insert(article: Article)

    // Methode zum Löschen eines Artikels aus der Datenbank.
    @Delete
    suspend fun delete(article: Article)

    // Methode zum Überprüfen, ob ein Artikel anhand seiner ID als Favorit markiert ist.
    @Query("SELECT * FROM article WHERE articleId = :id")
    suspend fun isFavorite(id: String): Article?

    // Methode zum Abrufen aller favorisierten Artikel aus der Datenbank.
    @Query("SELECT * FROM article")
    suspend fun getFavorites(): List<Article>
}