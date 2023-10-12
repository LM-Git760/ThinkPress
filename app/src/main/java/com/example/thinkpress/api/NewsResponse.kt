package com.example.thinkpress.api

data class NewsResponse(
    val articles: List<Article>
)

data class Article(
    val title: String,
    val description: String,
    //Andere Felder
)



