package com.example.greenvalley.dataRepository

import androidx.arch.core.util.Function
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException

typealias DocumentSnapShotsOrException= DataOrException<List<DocumentSnapshot>?, FirebaseFirestoreException>

internal class DeserializeDocSnapshots <T>(
    private val deserializer: DocumentSnapshotDeserializer<T>
) : Function<DocumentSnapShotsOrException,QueryResultsOrException<T,Exception>>{

    override fun apply(input: DocumentSnapShotsOrException): QueryResultsOrException<T, Exception> {
        val(snapshots,exception) = input
        return when{
            snapshots != null -> return try {
                val items = snapshots.map { snapshot ->
                    val data = deserializer.deserialize(snapshot)
                    object : QueryItem<T> {
                        override val item: T
                            get() = data
                        override val id: String
                            get() = snapshot.id
                    }
                }
                QueryResultsOrException(items, null)
            }
            catch (e:Deserializer.DeserializerException){
                QueryResultsOrException(null,e)
            }
            exception != null -> QueryResultsOrException(null, exception)

            else -> QueryResultsOrException(null, Exception("Both data and exception were null"))
        }
    }
}