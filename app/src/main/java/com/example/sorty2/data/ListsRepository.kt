package com.example.sorty2.data
//
//import androidx.compose.runtime.State
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.snapshots.SnapshotStateList
//import javax.inject.Inject
//import dagger.hilt.android.lifecycle.HiltViewModel
//
//class ListsRepository @Inject constructor(){
//    private val customLists = mutableStateListOf<CustomList>()
//    private val listItems = mutableStateListOf<ListItem>()
//    private var listIdCounter = 1
//    private var itemIdCounter = 1
//
//    fun getCustomLists(): SnapshotStateList<CustomList> {
//        return customLists
//    }
//
//    suspend fun addList(name: String) {
//        customLists.add(CustomList(listIdCounter++, name, emptyList()))
//    }
//
//    suspend fun addItem(listId: Int, content: String) {
//        val newItem = ListItem(itemIdCounter++, listId, content)
//        listItems.add(newItem)
//        val list = customLists.find { it.id == listId }
//        list?.let {
//            val updatedItems = list.items + newItem
//            customLists[customLists.indexOf(list)] = list.copy(items = updatedItems)
//        }
//    }
//
//    // Implement update and delete functions
//}
