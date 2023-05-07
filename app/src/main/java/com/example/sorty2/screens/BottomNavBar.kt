package com.example.sorty2.screens

import androidx.annotation.DrawableRes
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import com.example.sorty2.navItems
import com.example.sorty2.R.drawable.*

@Composable
fun BottomNavBar(
    navController: NavHostController
) {
    val icons = listOf(
        vectorDrawable(home_icon),
        vectorDrawable(lists_icon),
        vectorDrawable(expenses_icon),
        vectorDrawable(tasks_icon),
    )
    Surface {
        NavigationBar {
            navItems.forEachIndexed { index, screen ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            icons[index],
                            contentDescription = screen.route
                        )
                    },
                    label = { Text(screen.route) },
                    selected = navController.currentBackStackEntry?.destination?.route == screen.route,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

/**
 * A composable that converts drawable to ImageVerctor.
 */
@Composable
fun vectorDrawable(@DrawableRes id: Int): ImageVector = ImageVector.vectorResource(id)