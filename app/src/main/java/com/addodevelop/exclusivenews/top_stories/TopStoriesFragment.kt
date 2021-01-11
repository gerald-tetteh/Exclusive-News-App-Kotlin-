package com.addodevelop.exclusivenews.top_stories

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.addodevelop.exclusivenews.ExclusiveApplication
import com.addodevelop.exclusivenews.databinding.TopStoriesFragmentBinding
import com.addodevelop.exclusivenews.home_page.HomePageFragmentDirections

class TopStoriesFragment : Fragment() {

    companion object {
        fun newInstance() = TopStoriesFragment()
    }

    private lateinit var binding: TopStoriesFragmentBinding
    private lateinit var viewModel: TopStoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TopStoriesFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val application = requireActivity().application
        val viewModelFactory = TopStoriesViewModelFactory(sharedPreferences,application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(TopStoriesViewModel::class.java)
        binding.viewModel = viewModel
        binding.topStoriesRecyclerView.adapter = TopStoriesAdapter(TopStoriesClickListener { itemView, newsItem ->
            val extras = FragmentNavigatorExtras(itemView to "big_image")
            findNavController().navigate(
                    HomePageFragmentDirections.actionHomePageFragmentToNewsDetailFragment(newsItem),
                    extras
            )
        })
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setHeader(binding)
        }
        (requireActivity().application as ExclusiveApplication).topStoriesReload.observe(viewLifecycleOwner, {
            it?.let {
                viewModel.reloadStories()
                (requireActivity().application as ExclusiveApplication).onDoneUpdatingTopStories()
            }
        })
        return binding.root
    }

    private fun setHeader(binding: TopStoriesFragmentBinding) {
        (binding.topStoriesRecyclerView.layoutManager as GridLayoutManager).spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = when (position) {
                0 -> 2
                else -> 1
            }
        }
    }

}