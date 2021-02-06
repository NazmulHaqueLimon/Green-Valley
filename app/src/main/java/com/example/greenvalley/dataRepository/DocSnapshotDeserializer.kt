package com.example.greenvalley.dataRepository

import com.example.greenvalley.ui.listItems.Item
import com.google.firebase.firestore.DocumentSnapshot

internal interface DocumentSnapshotDeserializer<T> : Deserializer<DocumentSnapshot, T>


internal class DocSnapshotDeserializer :DocumentSnapshotDeserializer<Item> {
    override fun deserialize(input: DocumentSnapshot): Item {
        //val id =input.id
        val name =input.getString("name")?:
            throw Deserializer.DeserializerException("name not found")

        //return Item(id,name)
        return Item(name)

    }
}