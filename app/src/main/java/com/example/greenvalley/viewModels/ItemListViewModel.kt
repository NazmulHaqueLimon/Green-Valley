package com.example.greenvalley.viewModels

import android.content.ContentValues.TAG
import android.service.controls.ControlsProviderService
import android.util.Log
import androidx.lifecycle.*
import com.example.greenvalley.dataRepository.*
import com.example.greenvalley.ui.listItems.DisplayItem
import com.example.greenvalley.ui.listItems.Item
import com.example.greenvalley.ui.listItems.toDisplayItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Array.get
import javax.inject.Inject

 @HiltViewModel
 class ItemListViewModel @Inject  constructor(
     private val itemListRepository :ItemListRepository,
     private val savedStateHandle: SavedStateHandle
    ) : ViewModel() {


    //var items = MutableLiveData<filteredDisplayItemResults>()
     private val filters= MutableLiveData<List<String>>()
    // private val filters: LiveData<List<String>> = MutableLiveData()
     //val filters:LiveData<List<String>> =_filters

      fun setFilters(filterItems :List<String>){
          filters.value=filterItems

     }
     // Create a LiveData with a String
     val currentName: MutableLiveData<String> by lazy {
         MutableLiveData<String>()
     }
     val items:LiveData<filteredDisplayItemResults> =filters.switchMap {
         loadFilteredItems(it)

     }

     private fun loadFilteredItems(filterItems: List<String>): LiveData<filteredDisplayItemResults> {
         val result =  MutableLiveData<filteredDisplayItemResults>()
         viewModelScope.launch {
             result.value = filterItems.let {list->

                 itemListRepository.getFilteredItems(list).map { result->

                     val converted = result.data?.map {queryItem->
                         DisplayQueryItem(queryItem)
                     }
                     val exception = result.exception
                     filteredDisplayItemResults(converted,exception)

                 }
             }.value

         }
         return result

     }
     /**val items =filters.switchMap { list->
         liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
             emit(itemListRepository.getFilteredItems(list).map { result->
                 val converted = result.data?.map {
                     DisplayQueryItem(it)
                 }
                 val exception =result.exception
                 filteredDisplayItemResults(converted,exception)
             })
         }
     }*/



     companion object {
         private const val FILTERS_SAVED_STATE_KEY = "filters_key"
     }

        /**
         *
         * private fun loadFilteredItems(filterItems: List<String>) {
           viewModelScope.launch {
               val filteredItems: LiveData<filteredItemsQueryResultOrException> = filterItems.let {
                   itemListRepository.getFilteredItems(it)
               }
                items= filteredItems.map { queryResult ->
                    val result =queryResult.data?.map {
                        DisplayQueryItem(it)
                    }

                    val exception = queryResult.exception
                    filteredDisplayItemResults(result,exception)

                } as MutableLiveData<filteredDisplayItemResults>

           }

       }*/

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
/**typealias QueryResultsOrException<T, E> = DataOrException<List<QueryItem<T>>, E>
typealias filteredItemsQueryResultOrException =QueryResultsOrException<Item,Exception>
 */
// val filteredItems: LiveData<FilteredDisplayItemResults> = Transformations.map(items){

