package com.addodevelop.exclusivenews.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

enum class NetWorkStatus {LOADING, DONE, ERROR}

private const val BASE_URL = "https://newsapi.org/v2/"

private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getHeadlines(@Query("country") country:String, @Query("apiKey") apiKey:String, @Query("pageSize") pageSize:Int = 100): JsonNewsItem

    @GET("sources")
    suspend fun getSources(@Query("country") country:String, @Query("apiKey") apiKey: String): JsonSourceItem

    @GET("top-headlines")
    suspend fun getCategory(@Query("country") country:String, @Query("category") category:String, @Query("apiKey") apiKey: String): JsonNewsItem
}

object NewsApi {
    val retrofitService: NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }
}