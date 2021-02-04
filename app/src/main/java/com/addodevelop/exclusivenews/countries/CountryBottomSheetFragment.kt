package com.addodevelop.exclusivenews.countries

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.addodevelop.exclusivenews.R
import com.addodevelop.exclusivenews.databinding.FragmentCountryBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CountryBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentCountryBottomSheetBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val application = requireActivity().application
        val viewModelFactory = CountriesBottomSheetViewModelFactory(sharedPreferences,application)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(CountryBottomSheetViewModel::class.java)
        binding.countriesListView.adapter = CountriesListAdapter(application.applicationContext, R.layout.countries_list_item, availableCountries as MutableList<Country>, CountriesClickListener {
            viewModel.onAddValueToSharedPreferences(it)
        })
        viewModel.addValueToSharedPreferences.observe(viewLifecycleOwner, {
            if (it) {
                if (viewModel.errorOccurred != null) {
                    val toastText: String = if (viewModel.errorOccurred == true) "An Error Occurred" else "Country Changed"
                    Toast.makeText(application.applicationContext,toastText,Toast.LENGTH_LONG).show()
                }
                findNavController().navigateUp()
                viewModel.onDoneAddingValueToSharedPreferences()
            }
        })
        return binding.root
    }

}