package com.addodevelop.exclusivenews.news_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.addodevelop.exclusivenews.network.NewsItem

@Suppress("UNCHECKED_CAST")
class NewsDetailViewModelFactory(val newsItem: NewsItem): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsDetailViewModel::class.java)) {
            return NewsDetailViewModel(newsItem) as T
        }
        throw IllegalArgumentException("Wrong Class")
    }
}