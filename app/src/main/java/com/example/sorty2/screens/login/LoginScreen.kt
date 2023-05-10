package com.example.sorty2.screens.login

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sorty2.R
import com.example.sorty2.Screen
import com.example.sorty2.screens.sign_in.SignInState

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: FirebaseAuthViewModel,
    state: SignInState,
    onSignInClick: () -> Unit,
    onFinish: () -> Unit
) {
    var email by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }

    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    BackHandler { onFinish() }

    val currentUser by authViewModel.currentUser.collectAsState()
    LaunchedEffect(currentUser) {
        Log.d("LoginScreen", "Current user: $currentUser")
        if (currentUser != null) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.full_logo),
            contentDescription = "Logo",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
        )
        Spacer(modifier = Modifier.height(50.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                authViewModel.signIn(email.text, password.text)
                    .addOnSuccessListener {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
            }
        ) {
            Text(stringResource(R.string.signin))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate(Screen.SignUp.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            }
        ) {
            Text(stringResource(R.string.signup))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onSignInClick) {
            Text(text = stringResource(R.string.google_sign_in))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add a Text component to display the error message
        val signInError by authViewModel.signInError.collectAsState()
        if (signInError != null) {
            Text(
                text = signInError!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}