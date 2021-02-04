package com.addodevelop.exclusivenews.network

data class JsonSourceItem(
        val status: String,
        val message: String?,
        val sources: List<SourceItem>?
)
