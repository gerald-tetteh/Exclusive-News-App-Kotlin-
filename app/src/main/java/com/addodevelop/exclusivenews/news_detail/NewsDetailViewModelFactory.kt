package com.addodevelop.exclusivenews.news_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.addodevelop.exclusivenews.network.NewsItem
import com.addodevelop.exclusivenews.news_database.NewsDatabaseDao

@Suppress("UNCHECKED_CAST")
class NewsDetailViewModelFactory(val newsItem: NewsItem, private val databaseDao: NewsDatabaseDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsDetailViewModel::class.java)) {
            return NewsDetailViewModel(newsItem,databaseDao) as T
        }
        throw IllegalArgumentException("Wrong Class")
    }
}