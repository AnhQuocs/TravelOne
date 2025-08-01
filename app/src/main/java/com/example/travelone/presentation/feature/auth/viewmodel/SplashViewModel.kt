package com.example.travelone.presentation.feature.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.travelone.domain.usecase.auth.CheckUserLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkUserLoggedInUseCase: CheckUserLoggedInUseCase
) : ViewModel() {

    private val _startDestination = MutableStateFlow("dashboard")
    val startDestination: StateFlow<String> = _startDestination

    init {
        if (checkUserLoggedInUseCase()) {
            _startDestination.value = "main"
        } else {
            _startDestination.value = "dashboard"
        }
        Log.d("SplashViewModel", "$startDestination")
    }
}