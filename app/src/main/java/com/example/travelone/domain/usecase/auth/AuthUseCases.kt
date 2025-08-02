package com.example.travelone.domain.usecase.auth

import com.example.travelone.domain.model.auth.User
import com.example.travelone.domain.repository.auth.AuthRepository

data class AuthUseCase(
    val signUp: SignUpUseCase,
    val login: LoginUseCase,
    val logout: LogoutUseCase,
    val getCurrentUser: GetCurrentUserUseCase
)

class SignUpUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String, username: String) = repository.signUp(email, password, username)
}

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) = repository.login(email, password)
}

class LogoutUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke() = repository.logout()
}

class GetCurrentUserUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): User? {
        return repository.getCurrentUserFromFirestore()
    }
}