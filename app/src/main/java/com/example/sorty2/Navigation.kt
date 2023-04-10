package com.example.sorty2

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sorty2.screens.expenses.ExpensesScreen
import com.example.sorty2.screens.home.HomeScreen
import com.example.sorty2.screens.lists.ListsScreen
import com.example.sorty2.screens.tasks.TasksScreen

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String,
) {
    // Define the NavHost
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Home.title) { HomeScreen() }
        composable(Screen.Lists.title) { ListsScreen() }
        composable(Screen.Expenses.title) { ExpensesScreen() }
        composable(Screen.Tasks.title) { TasksScreen() }
    }
}