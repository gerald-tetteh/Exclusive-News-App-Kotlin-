package com.addodevelop.exclusivenews.sources

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.addodevelop.exclusivenews.R
import com.addodevelop.exclusivenews.apiKey
import com.addodevelop.exclusivenews.network.NetWorkStatus
import com.addodevelop.exclusivenews.network.NewsApi
import com.addodevelop.exclusivenews.network.SourceItem
import kotlinx.coroutines.*
import java.lang.Exception

class SourcesViewModel(private val sharedPreferences: SharedPreferences, application: Application) : AndroidViewModel(application) {

    private val  viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main  + viewModelJob)

    private val _sources = MutableLiveData<List<SourceItem>>()
    val sources: LiveData<List<SourceItem>>
        get() = _sources

    private val _networkStatus = MutableLiveData<NetWorkStatus>()
    val networkStatus: LiveData<NetWorkStatus>
        get() = _networkStatus

    private fun getSources() {
        uiScope.launch {
            _networkStatus.value = NetWorkStatus.LOADING
            try {
                val country = sharedPreferences.getString(getApplication<Application>().getString(R.string.country_iso_key),"us")
                _sources.value =  NewsApi.retrofitService.getSources(country!!, apiKey).sources
                _networkStatus.value = NetWorkStatus.DONE
            } catch (e: Exception) {
                _networkStatus.value = NetWorkStatus.ERROR
            }
        }
    }

    init {
        getSources()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}