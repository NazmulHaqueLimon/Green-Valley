 package com.example.greenvalley.viewModels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.greenvalley.dataRepository.ItemListRepository

class ItemListViewModel @ViewModelInject constructor (
    itemListRepository :ItemListRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
    ) :ViewModel(){


}