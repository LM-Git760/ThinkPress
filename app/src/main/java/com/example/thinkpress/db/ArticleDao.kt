package com.example.thinkpress.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.thinkpress.api.Article
import com.example.thinkpress.api.Favorite

// Definition des DAO (Data Access Object) zur Interaktion mit der Datenbank.
@Dao
interface ArticleDao {

    // Methode zum Einfügen eines Artikels in die Datenbank.
    // OnConflictStrategy.REPLACE sorgt dafür, dass bei Konflikten der existierende Eintrag ersetzt wird.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    // Methode zum Löschen eines Artikels aus der Datenbank.
    @Delete
    suspend fun delete(article: Article)

    // Methode zum Löschen eines Artikels anhand seiner ID.
    @Query("DELETE FROM article WHERE articleId = :id")
    suspend fun deleteById(id: String)

    // Methode zum Überprüfen, ob ein Artikel anhand seiner ID als Favorit markiert ist.
    @Query("SELECT * FROM article WHERE articleId = :id")
    suspend fun isFavorite(id: String): Article?

    // Methode zum Abrufen aller favorisierten Artikel aus der Datenbank.
    @Query("SELECT * FROM article WHERE isFavorite = 1")
    fun getFavoriteArticles(): LiveData<List<Favorite>>

}
