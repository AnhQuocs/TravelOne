package com.example.travelone.domain.usecase.auth

import com.example.travelone.domain.repository.auth.AuthRepository
import javax.inject.Inject

class CheckUserLoggedInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Boolean {
        return authRepository.isUserLoggedIn()
    }
}