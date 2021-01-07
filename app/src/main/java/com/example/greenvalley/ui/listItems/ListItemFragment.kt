package com.example.greenvalley.ui.listItems

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.greenvalley.R

import com.example.greenvalley.databinding.FragmentListItemBinding
import com.example.greenvalley.viewModels.ItemListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListItemFragment : Fragment() {

    private val viewModel:ItemListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding =FragmentListItemBinding.inflate(inflater,container,false)
        context ?: return binding.root

        val adapter=ItemListAdapter()
        binding.itemRecycler.adapter=adapter
        updateUi(adapter)

        setHasOptionsMenu(true)



        return binding.root

    }

    private fun updateUi(adapter: ItemListAdapter) {
        TODO("Not yet implemented")
    }





    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_plant_list, menu)
    }




}