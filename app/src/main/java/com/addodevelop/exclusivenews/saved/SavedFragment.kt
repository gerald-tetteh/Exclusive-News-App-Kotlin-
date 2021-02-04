package com.addodevelop.exclusivenews.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.addodevelop.exclusivenews.databinding.SavedFragmentBinding
import com.addodevelop.exclusivenews.home_page.HomePageFragmentDirections
import com.addodevelop.exclusivenews.news_database.NewsDatabase

class SavedFragment : Fragment() {

    companion object {
        fun newInstance() = SavedFragment()
    }

    private lateinit var binding: SavedFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val databaseDao = NewsDatabase.getInstance(requireActivity().applicationContext).newsDatabaseDao
        val viewModelFactory = SavedViewModelFactory(databaseDao)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(SavedViewModel::class.java)
        binding = SavedFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.savedRecyclerView.adapter = SavedAdapter(SavedClickListener { itemView, newsItem ->
            val extras = FragmentNavigatorExtras(itemView to "big_image")
            findNavController().navigate(
                HomePageFragmentDirections.actionHomePageFragmentToNewsDetailFragment(newsItem),
                extras
            )
        }, SavedLongClickListener { newsItem ->
            context?.let { viewModel.showDeleteAlert(it,newsItem) }
        })
        return binding.root
    }

}