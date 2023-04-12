package com.example.sorty2

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sorty2.screens.expenses.ExpensesScreen
import com.example.sorty2.screens.home.HomeScreen
import com.example.sorty2.screens.lists.ListsScreen
import com.example.sorty2.screens.settings.Settings
import com.example.sorty2.screens.sign_in.GoogleAuthUiClient
import com.example.sorty2.screens.sign_in.SignInScreen
import com.example.sorty2.screens.sign_in.SignInViewModel
import com.example.sorty2.screens.tasks.TasksScreen
import com.example.sorty2.ui.theme.Sorty2Theme
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Sorty2Theme {
                val navController = rememberNavController()
                val icons = listOf(
                    vectorDrawable(R.drawable.home_icon),
                    vectorDrawable(R.drawable.lists_icon),
                    vectorDrawable(R.drawable.expenses_icon),
                    vectorDrawable(R.drawable.tasks_icon),
                )
//                val navBackStackEntry by navController.currentBackStackEntryAsState()
//                val currentDestination = navBackStackEntry?.destination
//                val currentScreenIsNavItem = screenIsNavItem(currentDestination?.route)
                Scaffold(
//                    topBar = {
//                        Surface(tonalElevation = 1.dp) {
//                            TopAppBar(
//                                title = { Text("\uD83D\uDD25 FireNews") },
//                                navigationIcon = {
//                                    if (!currentScreenIsNavItem) {
//                                        IconButton(onClick = {
//                                            navController.popBackStack()
//                                        }) {
//                                            Icon(Icons.Filled.ArrowBack, contentDescription = "go back")
//                                        }
//                                    }
//                                },
////                    actions = {
////                        if (currentDestination?.route == Route.ArticleList.routeString) {
////                            IconButton(onClick = {
////                                articleViewModel.refreshPosts()
////                            }) {
////                                Icon(Icons.Filled.Refresh, contentDescription = stringResource(R.string.refresh))
////                            }
////                        }
////                    }
//                            )
//                        }
//                    },
                    bottomBar = {
                        if (navController.currentBackStackEntry?.destination?.route != "sign_in") {
                            // Define the bottom navigation bar
                            NavigationBar {
                                navItems.forEachIndexed { index, screen ->
                                    NavigationBarItem(
//                                    icon = { Icon(screen.icon, contentDescription = screen.title) },
                                        icon = {
                                            Icon(
                                                icons[index],
                                                contentDescription = screen.route.routeString
                                            )
                                        },
                                        label = { Text(screen.route.routeString) },
                                        selected = navController.currentBackStackEntry?.destination?.route == screen.route.routeString,
                                        onClick = {
                                            navController.navigate(screen.route.routeString) {
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
                    }
                ) { innerPadding ->
                    // Define the NavHost
                    NavHost(
                        navController = navController,
                        startDestination = Route.SignIn.routeString,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Route.Home.routeString) { HomeScreen(navController) }
                        composable(Route.Lists.routeString) { ListsScreen() }
                        composable(Route.Expenses.routeString) { ExpensesScreen() }
                        composable(Route.Tasks.routeString) { TasksScreen() }

                        composable(Route.SignIn.routeString) {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(key1 = Unit) {
                                if (googleAuthUiClient.getSignedInUser() != null) {
                                    navController.navigate(Route.Home.routeString)
                                }
                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if (state.isSignInSuccessful) {
                                    Toast.makeText(
                                        applicationContext,
                                        getString(R.string.sign_in_successful),
                                        Toast.LENGTH_LONG
                                    ).show()

                                    navController.navigate(Route.Home.routeString)
                                    viewModel.resetState()
                                }
                            }

                            SignInScreen(
                                state = state,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                }
                            )
                        }
                        composable(Route.Settings.routeString) {
                            Settings(
                                userData = googleAuthUiClient.getSignedInUser(),
                                onSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(
                                            applicationContext,
                                            getString(R.string.signed_out),
                                            Toast.LENGTH_LONG
                                        ).show()

                                        navController.popBackStack()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

//private fun screenIsNavItem(route: String?): Boolean {
//    if (route == null) return false
//
//    return navItems.find { it.route.toString() == route } != null
//}

@Composable
fun vectorDrawable(@DrawableRes id: Int): ImageVector = ImageVector.vectorResource(id)