package com.example.greenvalley.viewModels

import androidx.lifecycle.*
import com.example.greenvalley.dataRepository.*
import com.example.greenvalley.ui.listItems.DisplayItem
import com.example.greenvalley.ui.listItems.Item
import com.example.greenvalley.ui.listItems.toDisplayItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

 @HiltViewModel
 class ItemListViewModel @Inject  constructor(
         private val itemListRepository :ItemListRepository,
         private val savedStateHandle: SavedStateHandle
    ) :ViewModel(){

    // private val filters = mutableListOf<String>()
      var items : LiveData<filteredDisplayItemResults>? = null


     fun setFilters(filterItems :List<String>){
         savedStateHandle.set(FILTERS_SAVED_STATE_KEY,filterItems)
         viewModelScope.launch {
             loadFilteredItems(filterItems)
         }
     }

     companion object {
         private const val FILTERS_SAVED_STATE_KEY = "filters_key"
     }
     //val plants =it


       suspend fun loadFilteredItems(filterItems: List<String>){
        //: LiveData<filteredDisplayItemResults>?
           val filteredItems: LiveData<filteredItemsQueryResultOrException> = filterItems.let {
               itemListRepository.getFilteredItems(it)
           }

            items = filteredItems?.map { queryResult ->
               val result =queryResult.data?.map {
                   DisplayQueryItem(it)
               }

               val exception = queryResult.exception
               filteredDisplayItemResults(result,exception)

           }

     }


     /**typealias QueryResultsOrException<T, E> = DataOrException<List<QueryItem<T>>, E>
typealias filteredItemsQueryResultOrException =QueryResultsOrException<Item,Exception>
 */
    // val filteredItems: LiveData<FilteredDisplayItemResults> = Transformations.map(items){

    // }
}


 typealias filteredDisplayItemResults=QueryResultsOrException<DisplayItem,Exception>


 //extending queryItem<displayItem>
 private data class DisplayQueryItem(private val _item:QueryItem<Item>)
     :QueryItem<DisplayItem>{

            private val convertedItem =_item.item.toDisplayItem()

            override val item: DisplayItem
               get() = convertedItem
            override val id:String
                get() = _item.id



        }