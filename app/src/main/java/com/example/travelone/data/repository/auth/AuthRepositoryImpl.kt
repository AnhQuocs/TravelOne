package com.example.travelone.data.repository.auth

import com.example.travelone.data.mapper.auth.toDomain
import com.example.travelone.data.mapper.auth.toDto
import com.example.travelone.data.model.auth.UserDto
import com.example.travelone.domain.model.auth.User
import com.example.travelone.domain.repository.auth.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): AuthRepository {
    override suspend fun signUp(email: String, password: String, username: String): Result<User> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: return Result.failure(Exception("User is null"))

            val user = User(uid, email, username)
            firestore.collection("users").document(uid).set(user.toDto()).await()

            Result.success(user)
        } catch (e: Exception) {
            auth.currentUser?.delete()?.await()
            Result.failure(e)
        }
    }

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: return Result.failure(Exception("User is null"))

            val snapshot = firestore.collection("users").document(uid).get().await()
            val userDto = snapshot.toObject(UserDto::class.java)
                ?: return Result.failure(Exception("User not found"))
            Result.success(userDto.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        auth.signOut()
    }

    override suspend fun getCurrentUserFromFirestore(): User? {
        val uid = auth.currentUser?.uid ?: return null
        val snapshot = firestore.collection("users").document(uid).get().await()
        val userDto = snapshot.toObject(UserDto::class.java) ?: return null
        return userDto.toDomain()
    }

    override fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}