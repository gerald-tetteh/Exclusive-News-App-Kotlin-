package com.addodevelop.exclusivenews.news_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.addodevelop.exclusivenews.network.NewsItem

@Database(entities = [NewsItem::class], version = 1, exportSchema = false)
abstract class NewsDatabase: RoomDatabase() {

    abstract val newsDatabaseDao: NewsDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getInstance(context: Context): NewsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NewsDatabase::class.java,
                        "saved_news_database"
                    ).fallbackToDestructiveMigration().build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }
}