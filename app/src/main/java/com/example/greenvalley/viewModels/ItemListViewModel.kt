 package com.example.greenvalley.viewModels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.greenvalley.dataRepository.ItemListRepository
import com.example.greenvalley.ui.filterItems.FilterItem
import com.example.greenvalley.ui.listItems.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

 class ItemListViewModel @ViewModelInject constructor (
      itemListRepository :ItemListRepository,
     @Assisted private val savedStateHandle: SavedStateHandle
    ) :ViewModel(){

     //private val  filters: MutableStateFlow<List<String>>=MutableStateFlow()
     private val filters: MutableStateFlow<List<String>?> = MutableStateFlow(
             savedStateHandle.get<List<String>>(FILTERS_SAVED_STATE_KEY)
     )
     init {
         viewModelScope.launch {
             filters.collect { newList ->
                 savedStateHandle.set(FILTERS_SAVED_STATE_KEY,newList)
             }
         }
     }


     fun setFilters(filterItems :List<String>){
         filters.value=filterItems
     }

     companion object {
         private const val FILTERS_SAVED_STATE_KEY = "filters_key"
     }



}