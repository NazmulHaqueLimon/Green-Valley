package com.example.greenvalley.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.greenvalley.R
import com.example.greenvalley.databinding.FragmentHomeViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeViewPagerFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding=FragmentHomeViewPagerBinding.inflate(inflater,container,false)

        val tabLayout =binding.tabs
        val viewPager=binding.viewPager

        viewPager.adapter=ViewPagerStateAdapter(this)

        /**Set the icon and text for each tab*/
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)


        return binding.root

    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            MY_GARDEN_PAGE_INDEX -> R.drawable.my_garden_tab_selector
            GARDENING_PAGE_INDEX -> R.drawable.gardening_tab_selector
            FILTER_ITEMS_PAGE_INDEX -> R.drawable.filter_items_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            MY_GARDEN_PAGE_INDEX -> getString(R.string.my_garden_title)
            GARDENING_PAGE_INDEX -> getString(R.string.gardening_title)
            FILTER_ITEMS_PAGE_INDEX -> getString(R.string.filter_items_title)
            else -> null
        }
    }
}


