package com.example.thinkpress.api

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface NewsApiService {

    companion object {
        fun create(context: Context): NewsApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://newsdata.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(NewsApiService::class.java)
        }
    }

    @Headers("x-api-key: pub_310178ef71a1b033f97594bf39bee90edfc10")
    @GET("v1/news/")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("lang") lang: String
    ): NewsResponse

}