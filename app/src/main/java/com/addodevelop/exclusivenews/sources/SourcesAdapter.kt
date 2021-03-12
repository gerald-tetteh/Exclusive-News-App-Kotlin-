package com.addodevelop.exclusivenews.sources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.addodevelop.exclusivenews.databinding.SourcesListItemBinding
import com.addodevelop.exclusivenews.network.SourceItem

class SourcesAdapter: ListAdapter<SourceItem,SourcesAdapter.SourcesViewHolder>(SourcesDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourcesViewHolder {
        return SourcesViewHolder.from(parent);
    }

    override fun onBindViewHolder(holder: SourcesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SourcesViewHolder private constructor(val binding: SourcesListItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(sourceItem: SourceItem) {
            binding.sourceItem = sourceItem
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SourcesViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = SourcesListItemBinding.inflate(inflater)
                return SourcesViewHolder(binding);
            }
        }
    }

}

class SourcesDiffUtil: DiffUtil.ItemCallback<SourceItem>() {
    override fun areItemsTheSame(oldItem: SourceItem, newItem: SourceItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SourceItem, newItem: SourceItem): Boolean {
        return oldItem == newItem
    }

}