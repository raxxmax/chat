package com.example.chat.feature.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SigninViewmodel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<SignInState>(SignInState.Nothing)
    val state = _state.asStateFlow()

    private val auth = FirebaseAuth.getInstance()

    fun SignIn(email: String, password: String) {
        viewModelScope.launch {
            _state.value = SignInState.Loading

            // Basic validation
            if (email.isEmpty() || password.isEmpty()) {
                _state.value = SignInState.Error("All fields are required")
                return@launch
            }

            // Firebase signin
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = task.result.user
                        if (user != null) {
                            _state.value = SignInState.Success
                        } else {
                            _state.value = SignInState.Error("Authentication failed")
                        }
                    } else {
                        _state.value =
                            SignInState.Error(task.exception?.message ?: "Sign in failed")
                    }
                }
        }
    }
}

sealed class SignInState {
    object Nothing : SignInState()
    object Loading : SignInState()
    object Success : SignInState()
    data class Error(val message: String) : SignInState()
}