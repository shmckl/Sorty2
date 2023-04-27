package com.example.sorty2.screens.sign_in

/**
 * This class represents the result of a sign in attempt.
 *
 * @param data the user data if the sign in was successful
 * @param errorMessage the error message if the sign in failed
 */
data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

/**
 * This class represents the user data.
 *
 * @param userId the user id
 * @param username the user name
 * @param profilePictureUrl the profile picture url
 */
data class UserData(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?
)