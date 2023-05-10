package com.example.sorty2

sealed class Screen(val route: String) {
    object Home : Screen("Home")
    object Lists : Screen("Lists")
    object Expenses : Screen("Expenses")
    object Tasks : Screen("Tasks")
    object Settings : Screen("Settings")
    object SignUp : Screen("sign_up")
    object Login : Screen("login")
}

val navItems = listOf(
    Screen.Home,
    Screen.Lists,
    Screen.Expenses,
    Screen.Tasks
)