package com.addodevelop.exclusivenews.sources

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class SourcesViewModelFactory(private val sharedPreferences: SharedPreferences, private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SourcesViewModel::class.java)) {
            return SourcesViewModel(sharedPreferences,application) as T
        }
        throw IllegalArgumentException("Wrong class")
    }
}