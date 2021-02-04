package com.addodevelop.exclusivenews.sources

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
    val viewModel: SourcesViewModel by lazy {
        ViewModelProvider(this).get(SourcesViewModel::class.java)
    }

    private lateinit var binding: SourcesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SourcesFragmentBinding.inflate(inflater)
        return binding.root
    }

}