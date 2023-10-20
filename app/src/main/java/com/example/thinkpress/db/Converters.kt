package com.example.thinkpress.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    // Methode zur Konvertierung einer Liste von Strings in einen JSON-String.
    @TypeConverter
    fun fromListToString(list: List<String>?): String {
        return if (list == null) {
            ""
        } else {
            Gson().toJson(list)
        }
    }

    // Methode zur Konvertierung eines JSON-Strings zurück in eine Liste von Strings.
    @TypeConverter
    fun fromStringToList(string: String?): List<String> {
        return if (string.isNullOrEmpty()) {
            emptyList()
        } else {
            val type = object : TypeToken<List<String>>() {}.type
            Gson().fromJson(string, type)
        }
    }
}
