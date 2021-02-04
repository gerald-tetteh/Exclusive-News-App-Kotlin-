package com.addodevelop.exclusivenews.network

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "news_item_table")
@Parcelize
data class NewsItem(
    @Ignore
    var source: Source?,
    @ColumnInfo(name = "author")
    var author: String?,
    @PrimaryKey(autoGenerate = false)
    var title: String,
    @ColumnInfo(name = "description")
    var description: String?,
    @ColumnInfo(name = "url")
    var url: String?,
    @ColumnInfo(name = "urlToImage")
    var urlToImage: String?,
    @ColumnInfo(name = "publishedAt")
    var publishedAt: String?,
    @ColumnInfo(name = "content")
    var content: String?
): Parcelable {
    constructor(
        author: String?, title: String, description: String?, url: String?, urlToImage: String?, publishedAt: String?, content: String?)
            : this(null,author,title,description,url,urlToImage,publishedAt,content)
}

@Parcelize
data class Source (
    val id: String?,
    val name: String?
): Parcelable