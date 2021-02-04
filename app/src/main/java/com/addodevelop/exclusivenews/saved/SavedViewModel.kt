package com.addodevelop.exclusivenews.saved

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.addodevelop.exclusivenews.network.NewsItem
import com.addodevelop.exclusivenews.news_database.NewsDatabaseDao
import kotlinx.coroutines.*
import java.lang.Exception

class SavedViewModel(private val databaseDao: NewsDatabaseDao) : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val newsItems = databaseDao.getNewsItems()

    private val _itemDeleted = MutableLiveData<Boolean>()
    val itemDeleted: LiveData<Boolean>
        get() = _itemDeleted
    val databaseStatus = Transformations.map(newsItems) { newsItems ->
        newsItems != null
    }

    fun deleteItem(newsItem: NewsItem) {
        uiScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    databaseDao.deleteItem(newsItem.title)
                }
                _itemDeleted.value = true
            } catch (e: Exception) {
                _itemDeleted.value = false
            }
        }
    }
    fun onDoneDeleting() {
        _itemDeleted.value = false
    }

    fun showDeleteAlert(context: Context, newsItem: NewsItem) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete News Item")
        builder.setMessage("Are you sure you want to delete this item ?")
        builder.setPositiveButton("YES") { _, _ ->
            deleteItem(newsItem)
            onDoneDeleting()
        }
        builder.setNegativeButton("CANCEL",null)
        builder.show()
    }

    override fun onCleared() {
        super.onCleared()
        uiScope.cancel()
    }
}