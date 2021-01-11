package com.addodevelop.exclusivenews.countries

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.addodevelop.exclusivenews.databinding.CountriesListItemBinding

class CountriesListAdapter(context: Context, resource: Int, private val objects: MutableList<Country>, private val clickListener: CountriesClickListener)
    : ArrayAdapter<Country>(context, resource, objects) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val binding = CountriesListItemBinding.inflate(inflater)
        val country = objects[position]
        binding.country = country
        binding.clickListener = clickListener
        return binding.root
    }
}

class CountriesClickListener(val onClickedListener: (country:Country) -> Unit) {
    fun onClicked(country: Country) = onClickedListener(country)
}