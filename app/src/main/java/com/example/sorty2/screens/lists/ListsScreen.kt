package com.example.sorty2.screens.lists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sorty2.screens.MyTopBar

@Composable
fun ListsScreen(
    navController: NavHostController,
) {
    val lists = remember {
        mutableStateListOf(
            "Shopping",
            "List 2",
            "List 3",
            "List 3",
            "List 3",
        )
    }
    var selectedItem by remember { mutableStateOf(0) }
    val itemList = remember {
        mutableStateListOf(
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 3",
            "Item 3",
            "Item 3",
            "Item 3",
            "Item 3",
            "Item 3",
            "Item 3",
            "Item 3",
            "Item 3",
            "Item 3",
        )
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                MyTopBar(
                    screen = "Lists",
                    isNavItem = true,
                    navController = navController,
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            },
        ) { values ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values)
            ) {
                LazyRow(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .height(70.dp)
                ) {
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
                    onAdd = { index, newItem -> /* add the logic to add an item at the specified index */ },
                    onModify = { index, newItem -> /* add the logic to modify an item */ },
                    onToggle = { index, isChecked -> /* add the logic to toggle the item */ }
                )
            }
        }
    }
}

@Composable
fun ListCard(title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Text(text = title, modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun CreateListButton(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = "Create new list",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun ListContent(
    itemList: List<String>,
    onAdd: (Int, String) -> Unit,
    onModify: (Int, String) -> Unit,
    onToggle: (Int, Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        LazyColumn {
            itemsIndexed(itemList) { index, item ->
                ListItem(
                    item = item,
                    onEnterPressed = { newItem -> onAdd(index + 1, newItem) },
                    onModify = { newItem -> onModify(index, newItem) },
                    onToggle = { isChecked -> onToggle(index, isChecked) }
                )
            }
            item {
                var newItem by remember { mutableStateOf("") }
                TextField(
                    value = newItem,
                    onValueChange = { value -> newItem = value },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        if (newItem.isNotBlank()) {
                            onAdd(itemList.size, newItem)
                            newItem = ""
                        }
                    }),
                    label = { Text("Add new item") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun ListItem(
    item: String,
    onEnterPressed: (String) -> Unit,
    onModify: (String) -> Unit,
    onToggle: (Boolean) -> Unit
) {
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
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { onEnterPressed("") }),
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListsScreenPreview() {
    ListsScreen(rememberNavController())
}

//preview list item
@Preview(showBackground = true)
@Composable
fun ListItemPreview() {
    ListItem(item = "Item 1", onModify = {}, onToggle = {}, onEnterPressed = {})
}