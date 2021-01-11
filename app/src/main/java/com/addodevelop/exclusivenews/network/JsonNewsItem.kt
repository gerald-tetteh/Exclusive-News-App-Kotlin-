package com.addodevelop.exclusivenews.network

data class JsonNewsItem(
    val status: String?,
    val totalResults: Int?,
    val articles: List<NewsItem>?,
    val message: String?
)