package com.addodevelop.exclusivenews.countries

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.addodevelop.exclusivenews.ExclusiveApplication
import com.addodevelop.exclusivenews.R
import kotlinx.coroutines.*

class CountryBottomSheetViewModel(private val sharedPreferences: SharedPreferences,
                                  application: Application
): AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _addValueToSharedPreferences = MutableLiveData<Boolean>()
    val addValueToSharedPreferences: LiveData<Boolean>
        get() = _addValueToSharedPreferences

    var errorOccurred: Boolean? = null

    fun onAddValueToSharedPreferences(country: Country) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                errorOccurred = try {
                    with(sharedPreferences.edit()) {
                        putString(getApplication<Application>().getString(R.string.country_iso_key),country.iso)
                        apply()
                    }
                    false
                } catch (e: Exception) {
                    true
                }
            }
            (getApplication() as ExclusiveApplication).setFragmentReload((!errorOccurred!!))
            _addValueToSharedPreferences.value = true
        }
    }
    fun onDoneAddingValueToSharedPreferences() {
        _addValueToSharedPreferences.value = false
        errorOccurred = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}