package com.addodevelop.exclusivenews.top_stories

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.addodevelop.exclusivenews.R
import com.addodevelop.exclusivenews.apik_keys.newsApiKey
import com.addodevelop.exclusivenews.network.NetWorkStatus
import com.addodevelop.exclusivenews.network.NewsApi
import com.addodevelop.exclusivenews.network.NewsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TopStoriesViewModel(private val sharedPreferences: SharedPreferences, application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _newsItems = MutableLiveData<List<NewsItem>>()
    val newsItems: LiveData<List<NewsItem>>
        get() = _newsItems
    private val _networkStatus = MutableLiveData<NetWorkStatus>()
    val netWorkStatus: LiveData<NetWorkStatus>
        get() = _networkStatus
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    init {
        getNewsItems()
    }

    private fun getNewsItems() {
        uiScope.launch {
            _networkStatus.value = NetWorkStatus.LOADING
            val country = sharedPreferences.getString(getApplication<Application>().getString(R.string.country_iso_key),"us")
            try {
                val jsonNewsItem = NewsApi.retrofitService.getHeadlines(country!!, newsApiKey)
                _newsItems.value = jsonNewsItem.articles
                _networkStatus.value = NetWorkStatus.DONE
            } catch (e: Exception) {
                _networkStatus.value = NetWorkStatus.ERROR
            }
        }
    }
    fun reloadStories() {
        getNewsItems()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}