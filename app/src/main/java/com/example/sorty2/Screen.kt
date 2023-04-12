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

sealed class Screen(val route: Route) {
    object Home : Screen(Route.Home)
    object Lists : Screen(Route.Lists)
    object Expenses : Screen(Route.Expenses)
    object Tasks : Screen(Route.Tasks)
}

sealed class Route(val routeString: String) {
    object Home : Route("home")
    object Lists : Route("lists")
    object Tasks : Route("tasks")
    object Expenses : Route("expenses")
    object Settings : Route("settings")
    object SignIn : Route("sing_in")
    object SignUp : Route("sign_up")
    object Login : Route("login")

    override fun toString(): String {
        return routeString
    }
}