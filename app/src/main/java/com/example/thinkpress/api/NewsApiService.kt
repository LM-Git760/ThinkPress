package com.example.thinkpress.api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("api/1/news")
    suspend fun getNews(
        @Query("apikey") apiKey: String,
        @Query("qInMeta") query: String,

    ): Response<NewsApiResponse>

    companion object {
        fun create(): NewsApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://newsdata.io/")
                .addConverterFactory(GsonConverterFactory.create())

                .build()

            return retrofit.create(NewsApiService::class.java)
        }
    }
}
