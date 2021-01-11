package com.addodevelop.exclusivenews.countries

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class CountriesBottomSheetViewModelFactory(private val sharedPreferences: SharedPreferences, private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryBottomSheetViewModel::class.java)) {
            return CountryBottomSheetViewModel(sharedPreferences,application) as T
        }
        throw IllegalArgumentException("Wrong Class")
    }
}