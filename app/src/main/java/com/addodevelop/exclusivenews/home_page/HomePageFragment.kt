package com.addodevelop.exclusivenews.home_page

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.addodevelop.exclusivenews.R
import com.addodevelop.exclusivenews.databinding.HomePageFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomePageFragment : Fragment() {

    companion object {
        fun newInstance() = HomePageFragment()
    }
    val viewModel: HomePageViewModel by lazy {
        ViewModelProvider(this).get(HomePageViewModel::class.java)
    }

    private lateinit var binding: HomePageFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val animation = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        postponeEnterTransition()
        binding = HomePageFragmentBinding.inflate(inflater)

        binding.viewPager.adapter = ViewPagerAdapter(childFragmentManager,lifecycle)
        TabLayoutMediator(binding.tabBar,binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.top_stories)
                1 -> tab.text = resources.getString(R.string.categories)
                2 -> tab.text = resources.getString(R.string.sources)
                3 -> tab.text = resources.getString(R.string.saved)
            }
        }.attach()
        binding.setCountryFab.setOnClickListener {
            findNavController().navigate(HomePageFragmentDirections.actionHomePageFragmentToCountryBottomSheetFragment())
        }
        container?.doOnPreDraw {
            startPostponedEnterTransition()
        }
        return binding.root
    }
}