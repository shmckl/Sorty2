package com.example.sorty2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sorty2.screens.expenses.ExpensesScreen
import com.example.sorty2.screens.home.HomeScreen
import com.example.sorty2.screens.lists.ListsScreen
import com.example.sorty2.screens.tasks.TasksScreen
import com.example.sorty2.ui.theme.Sorty2Theme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Sorty2Theme {
                val navController = rememberNavController()
                val screens = listOf(
                    Screen.Home,
                    Screen.Lists,
                    Screen.Expenses,
                    Screen.Tasks
                )
                val icons = listOf(
                    vectorDrawable(R.drawable.baseline_home_24),
                    vectorDrawable(R.drawable.baseline_checklist_24),
                    vectorDrawable(R.drawable.baseline_currency_pound_24),
                    vectorDrawable(R.drawable.baseline_add_task_24),
                )
                Scaffold(
                    bottomBar = {
                        // Define the bottom navigation bar
                        NavigationBar {
                            screens.forEachIndexed { index, screen ->
                                NavigationBarItem(
//                                    icon = { Icon(screen.icon, contentDescription = screen.title) },
                                    icon = { Icon(icons[index], contentDescription = screen.title) },
                                    label = { Text(screen.title) },
                                    selected = navController.currentBackStackEntry?.destination?.route == screen.title,
                                    onClick = {
                                        navController.navigate(screen.title) {
                                            // Pop up to the start destination of the graph to
                                            // avoid building up a large stack of destinations
                                            // on the back stack as users select items
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            // Avoid multiple copies of the same destination when
                                            // re-selecting the same item
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) {
                    // Define the NavHost
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.title
                    ) {
                        composable(Screen.Home.title) { HomeScreen() }
                        composable(Screen.Lists.title) { ListsScreen() }
                        composable(Screen.Expenses.title) { ExpensesScreen() }
                        composable(Screen.Tasks.title) { TasksScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun vectorDrawable(@DrawableRes id: Int): ImageVector = ImageVector.vectorResource(id)