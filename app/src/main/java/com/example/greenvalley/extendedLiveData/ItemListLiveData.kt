package com.example.greenvalley.extendedLiveData

import androidx.lifecycle.LiveData
import com.example.greenvalley.dataRepository.DataOrException
import com.example.greenvalley.ui.listItems.Item
import com.google.firebase.firestore.*

typealias DocumentSnapShotOrException=
        DataOrException<List<DocumentSnapshot>?, FirebaseFirestoreException >

class ItemListLiveData(
        //a query
        private val documentReference: DocumentReference)
    :LiveData<DocumentSnapShotOrException>(), EventListener<DocumentSnapshot> {

    private val fireStore =FirebaseFirestore.getInstance()
    private var listenerRegistration: ListenerRegistration ?=null

    override fun onActive() {
        super.onActive()
        listenerRegistration=documentReference.addSnapshotListener(this)
        //register with a query to listen

    }

    override fun onInactive() {
        super.onInactive()
        listenerRegistration!!.remove()
    }

    override fun onEvent(snap: DocumentSnapshot?, error: FirebaseFirestoreException?) {
        //if (snap !=null && snap.exists()){ }
        //val documents :List<DocumentSnapshot> = snap.toObject(Item)//snap.documents
        //postValue(DocumentSnapShotOrException(documents,error))
    }

}