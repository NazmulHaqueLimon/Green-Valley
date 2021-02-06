package com.example.greenvalley.ui.listItems

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.greenvalley.R

import com.example.greenvalley.databinding.FragmentListItemBinding
import com.example.greenvalley.viewModels.ItemListViewModel
import com.example.greenvalley.viewModels.filteredDisplayItemResults
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListItemFragment : Fragment() {

    private val viewModel: ItemListViewModel by viewModels()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentListItemBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = ItemListAdapter()
        binding.itemRecycler.adapter = adapter
        //updateUi(adapter)

        //typealias filteredDisplayItemResults=QueryResultsOrException<DisplayItem,Exception>
        val dataObserver =Observer<filteredDisplayItemResults>{
            if (it!=null){
                if (it.data !=null){
                    adapter.submitList(it.data.reversed())
                    //adapter getting a list of QueryItem<DisplayItem>
                }
                else if (it.exception != null) {
                    Log.e(TAG, "Error getting data", it.exception)

                }
            }
        }
        viewModel.items?.observe(viewLifecycleOwner,dataObserver)

        setHasOptionsMenu(true)



        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_plant_list,menu)
    }

}