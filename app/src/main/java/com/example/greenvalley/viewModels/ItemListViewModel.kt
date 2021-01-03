 package com.example.greenvalley.viewModels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.greenvalley.dataRepository.ItemListRepository
import com.example.greenvalley.ui.listItems.Item

 class ItemListViewModel @ViewModelInject constructor (
    itemListRepository :ItemListRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
    ) :ViewModel(){

     private val  filters: MutableLiveData<List<String>>?=null
      fun setFilters(items:List<String>){
        // filters.
     }



}