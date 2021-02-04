package com.addodevelop.exclusivenews.top_stories

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class TopStoriesViewModelFactory(private val sharedPreferences: SharedPreferences, val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopStoriesViewModel::class.java)) {
            return TopStoriesViewModel(sharedPreferences,application) as T
        }
        throw IllegalArgumentException("Wrong Class")
    }
}