package com.example.thinkpress.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.thinkpress.api.Article

// Annotation zur Definition der Datenbank mit den entsprechenden Entitäten und der Version.
@Database(entities = [Article::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    // Methode zur Bereitstellung des DAO-Objekts
    abstract fun articleDao(): ArticleDao

    companion object {
        // Volatile sorgt für die sofortige Sichtbarkeit der Änderungen an INSTANCE für alle Threads.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Methode zur Bereitstellung der Singleton-Datenbankinstanz.
        fun getInstance(context: Context): AppDatabase {
            // Wenn INSTANCE bereits erstellt wurde, wird es zurückgegeben.
            return INSTANCE ?: synchronized(this) {
                // Erstellung der Datenbankinstanz, wenn noch keine vorhanden ist.
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "favorite_articles"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // Rückgabe der neuen Datenbankinstanz.
                instance
            }
        }
    }
}
