package com.addodevelop.exclusivenews.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.addodevelop.exclusivenews.databinding.SavedFragmentBinding

class SavedFragment : Fragment() {

    companion object {
        fun newInstance() = SavedFragment()
    }
    val viewModel: SavedViewModel by lazy {
        ViewModelProvider(this).get(SavedViewModel::class.java)
    }

    private lateinit var binding: SavedFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SavedFragmentBinding.inflate(inflater)
        return binding.root
    }

}