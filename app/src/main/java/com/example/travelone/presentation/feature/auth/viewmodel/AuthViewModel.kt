package com.example.travelone.presentation.feature.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelone.domain.model.auth.User
import com.example.travelone.domain.usecase.auth.AuthUseCase
import com.example.travelone.presentation.feature.auth.util.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class AuthActionType {
    LOGIN, SIGN_UP
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCase
): ViewModel() {

    private val _authState = MutableStateFlow<Result<User>?>(null)
    val authState: StateFlow<Result<User>?> = _authState

    private val _lastAuthAction = MutableStateFlow<AuthActionType?>(null)
    val lastAuthAction: StateFlow<AuthActionType?> = _lastAuthAction

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError

    private val _passwordError = MutableStateFlow<String?> (null)
    val passwordError: StateFlow<String?> = _passwordError

    private val _usernameError = MutableStateFlow<String?>(null)
    val usernameError: StateFlow<String?> = _usernameError

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun signUp(email: String, password: String, username: String) {
        var isValid = true
        _lastAuthAction.value = AuthActionType.SIGN_UP

        if(!Validator.isValidEmail(email)) {
            _emailError.value = "Invalid email format"
            isValid = false
        } else {
            _emailError.value = null
        }

        if(!Validator.isValidPassword(password)) {
            _passwordError.value = "Password must be at least 8 characters long"
            isValid = false
        } else {
            _passwordError.value = null
        }

        if(!Validator.isValidUsername(username)) {
            _usernameError.value = "Username cannot be empty"
            isValid = false
        } else {
            _usernameError.value = null
        }

        if (!isValid) return

        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = authUseCases.signUp(email, password, username)
                _authState.value = result
            } catch (e: Exception) {
                _authState.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun login(email: String, password: String) {
        var isValid = true
        _lastAuthAction.value = AuthActionType.LOGIN

        if(!Validator.isValidEmail(email)) {
            _emailError.value = "Invalid email format"
            isValid = false
        } else {
            _emailError.value = null
        }

        if(!Validator.isValidPassword(password)) {
            _passwordError.value = "Password must be at least 8 characters long"
            isValid = false
        } else {
            _passwordError.value = null
        }

        if (!isValid) return

        viewModelScope.launch {
            _isLoading.value = true
            _authState.value = authUseCases.login(email, password)
            _isLoading.value = false
        }
    }

    fun logout() {
        viewModelScope.launch {
            _isLoading.value = true
            authUseCases.logout()
            _isLoading.value = false
        }
    }

    fun getCurrentUser(): User? = authUseCases.getCurrentUser()

    fun clearAuthState() {
        _authState.value = null
        _lastAuthAction.value = null
    }

    fun clearEmailError() { _emailError.value = null }
    fun clearPasswordError() { _passwordError.value = null }
    fun clearUsernameError() { _usernameError.value = null }

    val isLoggedIn: StateFlow<Boolean> = authState
        .map { result -> result?.isSuccess == true && result.getOrNull() != null }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )
}