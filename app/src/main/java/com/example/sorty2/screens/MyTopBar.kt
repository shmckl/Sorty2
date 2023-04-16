package com.example.sorty2.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    screen: String,
    isNavItem: Boolean = false,
    Button: @Composable () -> Unit = {},
    navController: NavHostController
) {
    Surface {
        TopAppBar(
            title = {
                Text(screen)
            },
            actions = {
                if (Button != {}) {
                    Button()
                }
            },
            navigationIcon = {
                if (!isNavItem) {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                }
            },
        )
    }
}