package com.example.travelone.domain.repository.auth

import com.example.travelone.domain.model.auth.User

interface AuthRepository {
    suspend fun signUp(email: String, password: String, username: String): Result<User>
    suspend fun login(email: String, password: String): Result<User>
    suspend fun logout()
    suspend fun getCurrentUserFromFirestore(): User?
    fun isUserLoggedIn(): Boolean
}