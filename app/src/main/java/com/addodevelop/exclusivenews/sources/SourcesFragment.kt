package com.addodevelop.exclusivenews.sources

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.addodevelop.exclusivenews.databinding.SourcesFragmentBinding

class SourcesFragment : Fragment() {

    companion object {
        fun newInstance() = SourcesFragment()
    }

    private lateinit var binding: SourcesFragmentBinding
    private lateinit var viewModel: SourcesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SourcesFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val sourcesViewModelFactory = SourcesViewModelFactory(requireActivity().getPreferences(Context.MODE_PRIVATE),requireActivity().application)
        viewModel = ViewModelProvider(this,sourcesViewModelFactory).get(SourcesViewModel::class.java)
        binding.viewModel = viewModel
        binding.sourcesRecyclerView.adapter = SourcesAdapter()
        return binding.root
    }

}