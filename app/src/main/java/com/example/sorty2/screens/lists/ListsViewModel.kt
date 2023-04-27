package com.example.sorty2.screens.lists

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sorty2.data.GroupList
import com.example.sorty2.data.ListItem
import com.example.sorty2.data.Resource
import com.example.sorty2.domain.ListsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val listsRepository: ListsRepository
) : ViewModel() {

    private var _lists = mutableStateOf<List<GroupList>>(emptyList())
    val lists: State<List<GroupList>> = _lists

    init {
        listsRepository.getListsRealtime()
            .onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _lists.value = resource.data!!
                        if (_lists.value.isEmpty()) {
                            createDefaultList()
                        }
                    }
                    is Resource.Error -> {
                        Log.w(TAG, resource.error!!)
                    }
                }
            }
            .launchIn(viewModelScope)
    }


    private fun createDefaultList() {
        val defaultList = GroupList(
            id = "default",
            name = "Shopping",
            items = listOf(
                ListItem(name = "Milk", isChecked = false),
                ListItem(name = "Bread", isChecked = false),
                ListItem(name = "Eggs", isChecked = false)
            )
        )
        viewModelScope.launch {
            // Add your logic to save the default list in the repository
            listsRepository.addList(defaultList)
            refreshLists()
        }
    }

    private fun refreshLists() {
        listsRepository.getListsOnce()
            .onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _lists.value = resource.data!!
                    }
                    is Resource.Error -> {
                        Log.w(TAG, resource.error!!)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun addItem(listIndex: Int, itemIndex: Int, newItem: String) {
        if (listIndex in _lists.value.indices) {
            val list = _lists.value[listIndex]
            val updatedItems =
                list.items.toMutableList().apply { add(itemIndex, ListItem(newItem, false)) }
            val updatedList = list.copy(items = updatedItems)

            updateList(listIndex, updatedList)
        }
    }

    fun updateItem(listIndex: Int, itemIndex: Int, newItem: String) {
        if (listIndex in _lists.value.indices) {
            val list = _lists.value[listIndex]
            if (itemIndex in list.items.indices) {
                val updatedItems = list.items.toMutableList()
                    .apply { this[itemIndex] = ListItem(newItem, this[itemIndex].isChecked) }
                val updatedList = list.copy(items = updatedItems)

                updateList(listIndex, updatedList)
            }
        }
    }

    fun toggleItem(listIndex: Int, itemIndex: Int, isChecked: Boolean) {
        if (listIndex in _lists.value.indices) {
            val list = _lists.value[listIndex]
            if (itemIndex in list.items.indices) {
                val updatedItems = list.items.toMutableList()
                    .apply { this[itemIndex] = ListItem(this[itemIndex].name, isChecked) }
                val updatedList = list.copy(items = updatedItems)

                updateList(listIndex, updatedList)
            }
        }
    }

    private fun updateList(listIndex: Int, updatedList: GroupList) {
        val updatedLists = _lists.value.toMutableList().apply { this[listIndex] = updatedList }
        _lists.value = updatedLists

        // Persist the updated list in the repository
        viewModelScope.launch {
            try {
                val listId = _lists.value[listIndex].id
                listsRepository.updateList(listId, updatedList)
            } catch (e: Exception) {
                Log.w(TAG, "Error updating list: ${e.message}")
            }
        }
    }

    fun createList(listName: String) {
        val newList =
            GroupList(id = UUID.randomUUID().toString(), name = listName, items = emptyList())
        _lists.value = _lists.value + newList

        viewModelScope.launch {
            try {
                listsRepository.addList(newList)
            } catch (e: Exception) {
                Log.w(TAG, "Error adding list: ${e.message}")
            }
        }
    }
}
