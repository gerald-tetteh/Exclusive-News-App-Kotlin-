package com.addodevelop.exclusivenews.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.addodevelop.exclusivenews.news_database.NewsDatabaseDao

@Suppress("UNCHECKED_CAST")
class SavedViewModelFactory(private val databaseDao: NewsDatabaseDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedViewModel::class.java)) {
            return SavedViewModel(databaseDao) as T
        }
        throw IllegalArgumentException("Wrong Class")
    }
}