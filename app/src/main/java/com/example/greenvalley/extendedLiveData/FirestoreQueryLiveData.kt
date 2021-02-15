package com.example.greenvalley.extendedLiveData

import androidx.lifecycle.LiveData
import com.example.greenvalley.dataRepository.DataOrException
import com.google.firebase.firestore.*

typealias DocumentSnapShotsOrException= DataOrException<List<DocumentSnapshot>?, FirebaseFirestoreException>

class FirestoreQueryLiveData(
        private val query: Query
        ): LiveData<DocumentSnapShotsOrException>(), EventListener<QuerySnapshot> {

   // private val fireStore = FirebaseFirestore.getInstance()
    private var listenerRegistration: ListenerRegistration?=null

    override fun onActive() {
        super.onActive()
        listenerRegistration=query.addSnapshotListener(this)
        //register with a query to listen
    }

    override fun onInactive() {
        super.onInactive()
        listenerRegistration!!.remove()
    }
    override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
        val documents =value?.documents
        postValue(documents?.let { DocumentSnapShotsOrException(it, error) })


    }
}