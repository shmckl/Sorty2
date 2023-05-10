package com.example.sorty2.screens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sorty2.R
import com.example.sorty2.screens.BottomNavBar
import com.example.sorty2.screens.MyTopBar
import com.example.sorty2.ui.theme.Sorty2Theme
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TasksScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                MyTopBar(
                    screen = "Tasks",
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
            }
        ) { values ->
            Column(
                Modifier
                    .padding(values)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = stringResource(id = R.string.upcoming_tasks),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(8.dp)
                )
//                TasksList()
            }
        }
    }
}
//
//@Composable
//fun TasksList() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        LazyColumn {
//            items(getSampleTasks()) { task ->
//                TaskItem(task)
//                Spacer(modifier = Modifier.height(8.dp))
//            }
//        }
//    }
//}
//
//@Composable
//fun TaskItem(task: Task) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(text = task.name, style = MaterialTheme.typography.displayMedium)
//        Text(
//            text = SimpleDateFormat("MMM dd, yyyy").format(task.date),
//            style = MaterialTheme.typography.displayMedium
//        )
//    }
//}
//
//data class Task(
//    val id: String,
//    val name: String,
//    val date: Date
//)
//
//fun getSampleTasks(): List<Task> {
//    val tasks = mutableListOf<Task>()
//    // Add your tasks here
//    return tasks
//}
//
//@Preview(showBackground = true)
//@Composable
//fun TasksScreenPreview() {
//    Sorty2Theme() {
//        TasksScreen(rememberNavController())
//    }
//}