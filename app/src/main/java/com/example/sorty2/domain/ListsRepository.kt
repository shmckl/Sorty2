package com.example.sorty2.domain

import com.example.sorty2.data.GroupList
import com.example.sorty2.data.Resource
import kotlinx.coroutines.flow.Flow

interface ListsRepository {
    fun getListsOnce(): Flow<Resource<List<GroupList>>>
    fun getListsRealtime(): Flow<Resource<List<GroupList>>>
    suspend fun addList(list: GroupList)
    suspend fun updateList(listId: String, list: GroupList)
}