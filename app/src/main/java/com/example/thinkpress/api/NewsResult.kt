package com.example.thinkpress.api

sealed class NewsResult{
    data class Success(val articles: List<Article>) : NewsResult()
    data class Failure(val code: Int, val message: String) : NewsResult()
}
