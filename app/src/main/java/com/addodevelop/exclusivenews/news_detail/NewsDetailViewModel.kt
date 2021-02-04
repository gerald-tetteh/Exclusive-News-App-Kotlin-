package com.addodevelop.exclusivenews.news_detail

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.addodevelop.exclusivenews.network.NewsItem
import com.addodevelop.exclusivenews.news_database.NewsDatabaseDao
import kotlinx.coroutines.*
import java.lang.Exception

class NewsDetailViewModel(val newsItem: NewsItem, private val databaseDao: NewsDatabaseDao) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _showSavedSnackBar = MutableLiveData<Boolean>()
    val showSnackBar: LiveData<Boolean>
        get() = _showSavedSnackBar
    private val _itemDeleted = MutableLiveData<Boolean>()
    val itemDeleted: LiveData<Boolean>
        get() = _itemDeleted

    fun saveNewsItem() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    databaseDao.insertItem(newsItem)
                } catch (e: Exception) {
                    Log.i("NewsDetailFragment","an error occurred: ${e.message}")
                }
            }
            _showSavedSnackBar.value = true
        }
    }
    fun onDoneShowingSnackBar() {
        _showSavedSnackBar.value = null
    }

    fun deleteNewsItem() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    databaseDao.deleteItem(newsItem.title)
                } catch (e: Exception) {
                    Log.i("NewsDetailFragment","an error occurred: ${e.message}")
                }
            }
            _itemDeleted.value = true
        }
    }
    fun onDoneUndo() {
        _itemDeleted.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

class UndoClickListener(val viewModel: NewsDetailViewModel): View.OnClickListener {
    override fun onClick(view: View?) {
        viewModel.deleteNewsItem()
    }
}