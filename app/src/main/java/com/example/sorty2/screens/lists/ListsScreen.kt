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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sorty2.data.ListItem
import com.example.sorty2.screens.BottomNavBar
import com.example.sorty2.screens.MyTopBar

/**
 * Screen to display all the lists.
 */
@Composable
fun ListsScreen(
    navController: NavHostController,
    viewModel: ListsViewModel = hiltViewModel()
) {
    val lists by viewModel.lists
    var showDialog by remember { mutableStateOf(false) }

    var selectedItem by remember { mutableStateOf(0) }
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
            bottomBar = {
                BottomNavBar(navController = navController)
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { /* add the logic to create a new list */ }) {
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
                    itemsIndexed(lists) { index, list ->
                        ListCard(title = list.name, onClick = { selectedItem = index })
                    }
                    item {
                        CreateListButton(onClick = { showDialog = true })
                        if (showDialog) {
                            NewListDialog(
                                onDismiss = { showDialog = false },
                                onConfirm = { newListName ->
                                    if (newListName.isNotBlank()) {
                                        viewModel.createList(newListName)
                                    }
                                    showDialog = false
                                }
                            )
                        }
                    }
                }
                if (lists.isNotEmpty()) {
                    ListContent(
                        items = lists[selectedItem].items,
                        onAdd = { index, newItem ->
                            viewModel.addItem(
                                selectedItem,
                                index,
                                newItem
                            )
                        },
                        onModify = { index, newItem ->
                            viewModel.updateItem(
                                selectedItem,
                                index,
                                newItem
                            )
                        },
                        onToggle = { index, isChecked ->
                            viewModel.toggleItem(
                                selectedItem,
                                index,
                                isChecked
                            )
                        }
                    )
                }
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
    items: List<ListItem>,
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
            itemsIndexed(items) { index, item ->
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
                            onAdd(items.size, newItem)
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
    item: ListItem,
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
            value = item.name,
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

////preview list item
//@Preview(showBackground = true)
//@Composable
//fun ListItemPreview() {
//    ListItem(item = "Item 1", onModify = {}, onToggle = {}, onEnterPressed = {})
//}