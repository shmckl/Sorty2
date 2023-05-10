package com.example.sorty2

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sorty2.screens.expenses.ExpensesScreen
import com.example.sorty2.screens.home.HomeScreen
import com.example.sorty2.screens.lists.ListsScreen
import com.example.sorty2.screens.login.FirebaseAuthViewModel
import com.example.sorty2.screens.login.LoginScreen
import com.example.sorty2.screens.settings.SettingsScreen
import com.example.sorty2.screens.sign_in.GoogleAuthUiClient
import com.example.sorty2.screens.sign_in.SignInViewModel
import com.example.sorty2.screens.signup.SignUpScreen
import com.example.sorty2.screens.tasks.TasksScreen
import com.example.sorty2.ui.theme.Sorty2Theme
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * The main activity of the app.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    private val authViewModel: FirebaseAuthViewModel by viewModels()

    // This is the main entry point of the app.
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            Sorty2Theme {
                val navController = rememberNavController()
                val coroutineScope = rememberCoroutineScope()
                Scaffold { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Login.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Login.route) {

                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()
                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)

                                            // Update the FirebaseAuthViewModel with the signed-in user
                                            signInResult.data?.let { userData ->
                                                authViewModel.updateCurrentUser(Firebase.auth.currentUser!!)
                                            }
                                        }
                                    }
                                }
                            )
                            val currentUser by authViewModel.currentUser.collectAsState()
                            LoginScreen(navController, authViewModel,
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
                                },
                                onFinish = { finish() }
                            )
                        }
                        composable(Screen.SignUp.route) {
                            SignUpScreen(navController, authViewModel)
                        }
                        composable(Screen.Home.route) {
                            if (authViewModel.currentUser.value != null) {
                                HomeScreen(navController, onFinish = { finish() })
                            } else {
                                navController.navigate(Screen.Login.route)
                            }
                        }
                        composable(Screen.Lists.route) {
                            ListsScreen(navController)
                        }
                        composable(Screen.Expenses.route) { ExpensesScreen(navController) }
                        composable(Screen.Tasks.route) { TasksScreen(navController) }

                        composable(Screen.Settings.route) {
                            SettingsScreen(
                                userData = googleAuthUiClient.getSignedInUser(),
                                onSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(
                                            applicationContext,
                                            getString(R.string.signed_out),
                                            Toast.LENGTH_LONG
                                        ).show()

                                        // Update the FirebaseAuthViewModel with the signed-out user
                                        authViewModel.signOut()

                                        navController.navigate(Screen.Login.route) {
                                            popUpTo(Screen.Home.route) { inclusive = true }
                                        }
                                    }
                                },
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}