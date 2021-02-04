package com.addodevelop.exclusivenews.saved

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.addodevelop.exclusivenews.databinding.SavedListItemBinding
import com.addodevelop.exclusivenews.network.NewsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedAdapter(private val savedClickListener: SavedClickListener): ListAdapter<NewsItem,SavedAdapter.SavedItemViewHolder>(SavedDiffUtil()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addItems(newsItems: List<NewsItem>?) {
        adapterScope.launch {
            val reversedItems = newsItems?.reversed()
            withContext(Dispatchers.Main) {
                submitList(reversedItems)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedItemViewHolder {
        return SavedItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SavedItemViewHolder, position: Int) {
        holder.bind(getItem(position), savedClickListener)
    }

    class SavedItemViewHolder private constructor(val binding: SavedListItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(newsItem: NewsItem, savedClickListener: SavedClickListener) {
            ViewCompat.setTransitionName(binding.cardListItem, "id_saved_${newsItem.title}")
            binding.newsItem = newsItem
            binding.savedClickListener = savedClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SavedItemViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = SavedListItemBinding.inflate(inflater)
                return SavedItemViewHolder(binding)
            }
        }
    }
}

class SavedClickListener(val onClickListener: (view: View, newsItem: NewsItem) -> Unit) {
    fun onClick(view: View, newsItem: NewsItem) = onClickListener(view, newsItem)
}

class SavedDiffUtil: DiffUtil.ItemCallback<NewsItem>() {
    override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
        return oldItem == newItem
    }
}