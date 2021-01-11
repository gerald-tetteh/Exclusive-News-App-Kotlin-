package com.addodevelop.exclusivenews.top_stories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.addodevelop.exclusivenews.databinding.TopStoriesGridItemBinding
import com.addodevelop.exclusivenews.databinding.TopStoriesHeaderItemBinding
import com.addodevelop.exclusivenews.network.NewsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TYPE_HEADER = 0
private const val TYPE_GRID_ITEM = 1

class TopStoriesAdapter(private val topStoriesClickListener: TopStoriesClickListener): ListAdapter<DataItem,RecyclerView.ViewHolder>(TopStoriesDiffUtil()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addNewsItemsWithHeader(newsItems: List<NewsItem>?) {
        adapterScope.launch {
            val finalItems = newsItems?.mapIndexed { index, newsItem ->
                when(index) {
                    0 -> DataItem.NewsGridHeader(newsItem)
                    else -> DataItem.NewsGridItem(newsItem)
                }
            }
            withContext(Dispatchers.Main) {
                submitList(finalItems)
            }
        }
    }

    fun addNewsItemsWithoutHeader(newsItems: List<NewsItem>) {
        adapterScope.launch {
            val finalItems = newsItems.map {
                DataItem.NewsGridItem(it)
            }
            withContext(Dispatchers.Main) {
                submitList(finalItems)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> NewsGridHeaderViewHolder.from(parent)
            TYPE_GRID_ITEM -> NewsGridItemViewHolder.from(parent)
            else -> throw ClassCastException("Unknown View Type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NewsGridItemViewHolder -> {
                val dataItem = getItem(position) as DataItem.NewsGridItem
                holder.bind(dataItem.newsItem,topStoriesClickListener)
            }
            is NewsGridHeaderViewHolder -> {
                val dataItem = getItem(position) as DataItem.NewsGridHeader
                holder.bind(dataItem.newsItem,topStoriesClickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.NewsGridHeader -> TYPE_HEADER
            is DataItem.NewsGridItem -> TYPE_GRID_ITEM
            else -> -1
        }
    }

    class NewsGridItemViewHolder private constructor(
        val binding: TopStoriesGridItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(newsItem: NewsItem, topStoriesClickListener: TopStoriesClickListener) {
            binding.newsItem = newsItem
            binding.newsImage.clipToOutline = true
            binding.onClickListener = topStoriesClickListener
            ViewCompat.setTransitionName(binding.newsImage,"id_${newsItem.title}")
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): NewsGridItemViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = TopStoriesGridItemBinding.inflate(inflater)
                return NewsGridItemViewHolder(binding)
            }
        }

    }

    class NewsGridHeaderViewHolder private constructor(
        val binding: TopStoriesHeaderItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(newsItem: NewsItem, topStoriesClickListener: TopStoriesClickListener) {
            binding.newsItem = newsItem
            binding.newsImage.clipToOutline = true
            binding.onClickListener = topStoriesClickListener
            ViewCompat.setTransitionName(binding.newsImage,"id_${newsItem.title}")
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): NewsGridHeaderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = TopStoriesHeaderItemBinding.inflate(inflater)
                return NewsGridHeaderViewHolder(binding)
            }
        }
    }
}

class TopStoriesClickListener(val onClickListener: (view: View, newsItem: NewsItem) -> Unit) {
    fun onClick(view: View, newsItem: NewsItem) = onClickListener(view, newsItem)
}

class TopStoriesDiffUtil: DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

sealed class DataItem {
    abstract val id: String

    data class NewsGridItem(val newsItem: NewsItem): DataItem() {
        override val id: String
            get() = newsItem.title!!
    }

    data class NewsGridHeader(val newsItem: NewsItem): DataItem() {
        override val id: String
            get() = newsItem.title!!
    }
}