package com.example.sorty2
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.CheckCircle
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.List
//import androidx.compose.material.icons.filled.ShoppingCart
//import androidx.compose.ui.graphics.vector.ImageVector

//sealed class Screen(val title: String, val icon: ImageVector) {
//    object Home : Screen("Home", Icons.Filled.Home)
//    object Lists : Screen("Lists", Icons.Filled.List)
//    object Expenses : Screen("Expenses", Icons.Filled.ShoppingCart)
//    object Tasks : Screen("Tasks", Icons.Filled.CheckCircle)
//}

sealed class Screen(val title: String) {
    object Home : Screen("Home")
    object Lists : Screen("Lists")
    object Expenses : Screen("Expenses")
    object Tasks : Screen("Tasks")
}