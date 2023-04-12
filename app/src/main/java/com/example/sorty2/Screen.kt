package com.example.sorty2

sealed class Screen(val route: Route) {
    object Home : Screen(Route.Home)
    object Lists : Screen(Route.Lists)
    object Expenses : Screen(Route.Expenses)
    object Tasks : Screen(Route.Tasks)
    object Settings : Screen(Route.Settings)
    object SignIn : Screen(Route.SignIn)
    object SignUp : Screen(Route.SignUp)
    object Login : Screen(Route.Login)
}

sealed class Route(val routeString: String) {
    object Home : Route("Home")
    object Lists : Route("Lists")
    object Tasks : Route("Tasks")
    object Expenses : Route("Expenses")
    object Settings : Route("Settings")
    object SignIn : Route("sing_in")
    object SignUp : Route("sign_up")
    object Login : Route("login")

    override fun toString(): String {
        return routeString
    }
}

val navItems = listOf(
    Screen.Home,
    Screen.Lists,
    Screen.Expenses,
    Screen.Tasks
)