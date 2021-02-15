package com.example.greenvalley.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.greenvalley.ui.filterItems.FilterItemsFragment
import com.example.greenvalley.ui.gardening101.GardeningFragment
import com.example.greenvalley.ui.myGarden.MyGardenFragment


const val MY_GARDEN_PAGE_INDEX = 0
const val FILTER_ITEMS_PAGE_INDEX = 1
const val GARDENING_PAGE_INDEX =2

class ViewPagerStateAdapter (fragment: Fragment):FragmentStateAdapter(fragment){
    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        MY_GARDEN_PAGE_INDEX to { MyGardenFragment() },
        FILTER_ITEMS_PAGE_INDEX to { FilterItemsFragment() },
        GARDENING_PAGE_INDEX to { GardeningFragment() }
    )

    override fun getItemCount(): Int {
        return tabFragmentsCreators.size
    }

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?:throw IndexOutOfBoundsException()
    }
}