package com.addodevelop.exclusivenews.categories

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.addodevelop.exclusivenews.ExclusiveApplication
import com.addodevelop.exclusivenews.databinding.CategoriesFragmentBinding

class CategoriesFragment : Fragment() {

    companion object {
        fun newInstance() = CategoriesFragment()
    }
    val viewModel: CategoriesViewModel by lazy {
        ViewModelProvider(this).get(CategoriesViewModel::class.java)
    }

    private lateinit var binding: CategoriesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CategoriesFragmentBinding.inflate(inflater)
        Log.i("CategoriesFragment","recreated")
        (requireActivity().application as ExclusiveApplication).categoriesReload.observe(viewLifecycleOwner, {
            it?.let {
                Log.i("CategoriesFragment","From Categories")
                (requireActivity().application as ExclusiveApplication).onDoneUpdatingCategories()
            }
        })
        return binding.root
    }

}