package com.example.sorty2.screens.sign_in

/**
 * This class represents the state of the sign in screen.
 *
 * @param isSignInSuccessful true if the sign in was successful
 * @param signInError the error message if the sign in failed
 */
data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)