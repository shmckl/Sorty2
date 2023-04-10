package com.example.sorty2

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.sorty2.ui.theme.Sorty2Theme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Sorty2App() {
    Sorty2Theme {
        val navController = rememberNavController()
        val screens = listOf(
            Screen.Home,
            Screen.Lists,
            Screen.Expenses,
            Screen.Tasks
        )
        val icons = listOf(
            vectorDrawable(R.drawable.home_icon),
            vectorDrawable(R.drawable.lists_icon),
            vectorDrawable(R.drawable.expenses_icon),
            vectorDrawable(R.drawable.tasks_icon),
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
        ) { innerPadding ->
            Navigation(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = Screen.Home.title
            )
        }
    }
}