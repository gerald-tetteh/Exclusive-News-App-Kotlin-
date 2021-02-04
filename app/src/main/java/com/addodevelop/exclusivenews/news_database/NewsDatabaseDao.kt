package com.addodevelop.exclusivenews.news_database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.addodevelop.exclusivenews.network.NewsItem

@Dao
interface NewsDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(newsItem: NewsItem)

    @Query("DELETE FROM news_item_table WHERE title = :title")
    suspend fun deleteItem(title: String)

    @Query("SELECT * FROM news_item_table")
    fun getNewsItems(): LiveData<List<NewsItem>>
}