package com.addodevelop.exclusivenews

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ExclusiveApplication: Application() {

    private val _topStoriesReload = MutableLiveData<Boolean>()
    val topStoriesReload: LiveData<Boolean>
        get() = _topStoriesReload

    private val _categoriesReload = MutableLiveData<Boolean>()
    val categoriesReload: LiveData<Boolean>
        get() = _categoriesReload

    fun setFragmentReload(value:Boolean) {
        _topStoriesReload.value = value
        _categoriesReload.value = value
    }

    fun onDoneUpdatingTopStories() {
        _topStoriesReload.value = null
    }
    fun onDoneUpdatingCategories() {
        _categoriesReload.value = null
    }
}