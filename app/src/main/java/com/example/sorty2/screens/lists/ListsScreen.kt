package com.example.sorty2.screens.lists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ListsScreen() {
    val lists = remember { mutableStateListOf("List 1", "List 2", "List 3", "List 3", "List 3", "List 3", "List 3", "List 3", "List 3", "List 3", "List 3", "List 3", "List 3", "List 3", "List 3", "List 3", "List 3", "List 3", "List 3", "List 3", "List 3", "List 3", "List 3", "List 3", "List 3", "List 3", "List 3", "List 3") }
    var selectedItem by remember { mutableStateOf(0) }
    val itemList = remember { mutableStateListOf("Item 1", "Item 2", "Item 3", "Item 3", "Item 3", "Item 3", "Item 3", "Item 3", "Item 3", "Item 3", "Item 3", "Item 3", "Item 3", "Item 3", "Item 3", "Item 3", "Item 3", "Item 3", "Item 3", "Item 3", "Item 3", "Item 3", "Item 3", "Item 3", "Item 3", "Item 3", "Item 3") }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyRow(modifier = Modifier.fillMaxWidth().height(80.dp)) {
            items(lists.size + 1) { index ->
                if (index < lists.size) {
                    ListCard(title = lists[index], onClick = { selectedItem = index })
                } else {
                    CreateListButton { /* add the logic to create a new list */ }
                }
            }
        }
        ListContent(
            itemList = itemList,
            onAdd = { /* add the logic to add an item */ },
            onModify = { index, newItem -> /* add the logic to modify an item */ },
            onToggle = { index, isChecked -> /* add the logic to toggle the item */ }
        )
    }
}

@Composable
fun ListCard(title: String, onClick: () -> Unit) {
    Card(modifier = Modifier.padding(8.dp).clickable(onClick = onClick)) {
        Text(text = title, modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun CreateListButton(onClick: () -> Unit) {
    Card(modifier = Modifier.padding(8.dp).clickable(onClick = onClick)) {
        Icon(Icons.Filled.Add, contentDescription = "Create new list", modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun ListContent(
    itemList: List<String>,
    onAdd: (String) -> Unit,
    onModify: (Int, String) -> Unit,
    onToggle: (Int, Boolean) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        LazyColumn {
            itemsIndexed(itemList) { index, item ->
                ListItem(
                    item = item,
                    onModify = { newItem -> onModify(index, newItem) },
                    onToggle = { isChecked -> onToggle(index, isChecked) }
                )
            }
        }
        TextField(
            value = "",
            onValueChange = { newItem -> onAdd(newItem) },
            label = { Text("Add new item") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun ListItem(item: String, onModify: (String) -> Unit, onToggle: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Checkbox(
            checked = false,
            onCheckedChange = onToggle,
            modifier = Modifier.padding(end = 8.dp)
        )
        TextField(
            value = item,
            onValueChange = onModify,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListsScreenPreview() {
    ListsScreen()
}
