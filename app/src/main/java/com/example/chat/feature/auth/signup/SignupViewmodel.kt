package com.example.chat.feature.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewmodel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<SignUpState>(SignUpState.Nothing)
    val state = _state.asStateFlow()

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun SignUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            _state.value = SignUpState.Loading

            // Basic validation
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                _state.value = SignUpState.Error("All fields are required")
                return@launch
            }

            if (password.length < 6) {
                _state.value = SignUpState.Error("Password must be at least 6 characters")
                return@launch
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = task.result.user
                        user?.updateProfile(
                            userProfileChangeRequest {
                                displayName = name
                            }
                        )?.addOnCompleteListener { profileTask ->
                            if (profileTask.isSuccessful) {
                                // Store user data in Firestore
                                saveUserToFirestore(user.uid, name, email)
                            } else {
                                _state.value =
                                    SignUpState.Error(
                                        profileTask.exception?.message ?: "Profile update failed"
                                    )
                            }
                        }
                    } else {
                        _state.value =
                            SignUpState.Error(task.exception?.message ?: "Sign up failed")
                    }
                }
        }
    }

    private fun saveUserToFirestore(userId: String, name: String, email: String) {
        val userData = hashMapOf(
            "name" to name,
            "email" to email,
            "userId" to userId,
            "createdAt" to System.currentTimeMillis()
        )

        firestore.collection("users")
            .document(userId)
            .set(userData)
            .addOnSuccessListener {
                _state.value = SignUpState.Success
            }
            .addOnFailureListener {
                _state.value = SignUpState.Error(it.message ?: "Failed to save user data")
            }
    }
}

sealed class SignUpState {
    object Nothing : SignUpState()
    object Loading : SignUpState()
    object Success : SignUpState()
    data class Error(val message: String) : SignUpState()
}