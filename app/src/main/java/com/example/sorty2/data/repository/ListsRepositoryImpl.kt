package com.example.sorty2.data.repository

import android.util.Log
import com.example.sorty2.data.GroupList
import com.example.sorty2.data.Resource
import com.example.sorty2.domain.ListsRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Repository implementation to expose lists data to the rest of the app.
 */
class ListsRepositoryImpl(
    private val database: FirebaseDatabase
) : ListsRepository {

    override fun getListsOnce(): Flow<Resource<List<GroupList>>> = callbackFlow {
        database.getReference("lists")
            .get()
            .addOnCompleteListener { task ->
                val response = if (task.isSuccessful) {
                    val dataSnapshot = task.result
                    val lists = dataSnapshot.children.mapNotNull { it.getValue<GroupList>() }

                    Resource.Success<List<GroupList>>(lists)
                } else {
                    Resource.Error<List<GroupList>>(task.exception?.localizedMessage.toString())
                }
                trySend(response).isSuccess
            }
        awaitClose {
            close()
        }
    }

    override fun getListsRealtime(): Flow<Resource<List<GroupList>>> = callbackFlow {
        trySend(Resource.Success(emptyList<GroupList>())).isSuccess // Add this line

        database.getReference("lists")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val lists = dataSnapshot.children.mapNotNull { it.getValue<GroupList>() }
                    trySend(Resource.Success<List<GroupList>>(lists)).isSuccess
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("lists", "Failed to read value.")
                    trySend(Resource.Error<List<GroupList>>(error = error.message)).isFailure
                }
            })

        awaitClose {
            close()
        }
    }


    override suspend fun addList(list: GroupList) {
        database.getReference("lists").child(list.id).setValue(list)
    }

    override suspend fun updateList(listId: String, list: GroupList) {
        database.getReference("lists")
            .child(listId)
            .setValue(list)
    }
}