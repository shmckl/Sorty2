package com.example.sorty2.screens.lists
//
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.sorty2.data.CustomList
//import com.example.sorty2.data.ListsRepository
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class ListsViewModel @Inject constructor(
//    private val repository: ListsRepository
//) : ViewModel() {
//    val customLists = repository.getCustomLists().asLiveData()
//    val selectedList = MutableLiveData<CustomList?>()
//
//    fun addList(name: String) {
//        viewModelScope.launch {
//            repository.addList(name)
//        }
//    }
//
//    fun addItem(content: String) {
//        viewModelScope.launch {
//            selectedList.value?.let { list ->
//                repository.addItem(list.id, content)
//            }
//        }
//    }
//
//    // Implement update and delete functions
//}
