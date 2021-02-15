package com.example.greenvalley.dataRepository

import android.content.ContentValues.TAG
import android.service.controls.ControlsProviderService
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.greenvalley.extendedLiveData.FirestoreQueryLiveData
import com.example.greenvalley.ui.listItems.Item
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemListRepository @Inject constructor()  {

    private val firestoreInstance =FirebaseFirestore.getInstance()
    private val documentRef =firestoreInstance.collection("products")

    private val deserializer =DocSnapshotDeserializer()

    suspend fun getFilteredItems(list: List<String>): LiveData<filteredItemsQueryResultOrException> {
        return withContext(Dispatchers.IO){
            Log.d(TAG,"items list is passing.through repository")
            val query =documentRef.whereArrayContainsAny("category",list)
            val queryLiveData =FirestoreQueryLiveData(query)
            //returns dataOrException of list of Documents
            Transformations.map(queryLiveData,DeserializeDocSnapshots(deserializer))

        }
    }

}


typealias QueryResultsOrException<T, E> = DataOrException<List<QueryItem<T>>, E>
typealias filteredItemsQueryResultOrException =QueryResultsOrException<Item,Exception>

/**
 * An item of data type T that resulted from a query. It adds the notion of
 * a unique id to that item.
 */
interface QueryItem<T> {
    val item: T
    val id: String
}

/**
 * interface BaseRepository {
fun getFilteredItems(list: List<String>):LiveData<filteredItemsQueryResultOrException>

}*/
//extending the queryItem interface
data class PlantQueryItem(
        private val plantItem : Item,
        private val plantId :String
):QueryItem<Item>{
    override val item: Item
        get() = plantItem

    override val id: String
        get() = plantId
}
