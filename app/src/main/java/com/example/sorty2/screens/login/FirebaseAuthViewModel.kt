package com.example.sorty2.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirebaseAuthViewModel @Inject constructor() : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    private val _currentUser = MutableStateFlow<FirebaseUser?>(null)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser

    init {
        _currentUser.value = auth.currentUser
    }

    private val _signInError = MutableStateFlow<String?>(null)
    val signInError: StateFlow<String?> = _signInError

    fun signIn(email: String, password: String): Task<AuthResult> {
        if (email.isBlank() || password.isBlank()) {
            _signInError.value = "Email and password cannot be empty."
            return Tasks.forCanceled<AuthResult>()
        }

        return auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _currentUser.value = auth.currentUser
                _signInError.value = null
            } else {
                _signInError.value = task.exception?.message ?: "Unknown error"
            }
        }
    }

    fun signUp(email: String, password: String, onSignUpSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _currentUser.value = auth.currentUser
                        onSignUpSuccess()
                    } else {
                        // Handle error
                    }
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

    fun signOut() {
        auth.signOut()
        _currentUser.value = null
    }

    fun updateCurrentUser(user: FirebaseUser) {
        _currentUser.value = user
    }
}