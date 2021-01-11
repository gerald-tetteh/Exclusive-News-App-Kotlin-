package com.addodevelop.exclusivenews.home_page

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.addodevelop.exclusivenews.categories.CategoriesFragment
import com.addodevelop.exclusivenews.categories.CategoriesViewModel
import com.addodevelop.exclusivenews.saved.SavedFragment
import com.addodevelop.exclusivenews.sources.SourcesFragment
import com.addodevelop.exclusivenews.top_stories.TopStoriesFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    val fragments = listOf(
        TopStoriesFragment.newInstance(),
        CategoriesFragment.newInstance(),
        SourcesFragment.newInstance(),
        SavedFragment.newInstance()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}