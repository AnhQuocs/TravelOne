package com.example.travelone.di

import com.example.travelone.data.repository.auth.AuthRepositoryImpl
import com.example.travelone.domain.repository.auth.AuthRepository
import com.example.travelone.domain.usecase.auth.AuthUseCase
import com.example.travelone.domain.usecase.auth.GetCurrentUserUseCase
import com.example.travelone.domain.usecase.auth.LoginUseCase
import com.example.travelone.domain.usecase.auth.LogoutUseCase
import com.example.travelone.domain.usecase.auth.SignUpUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    fun provideFireStore() = FirebaseFirestore.getInstance()

    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRepository = AuthRepositoryImpl(auth, firestore)

    @Provides
    fun provideAuthUseCase(repository: AuthRepository) = AuthUseCase(
        signUp = SignUpUseCase(repository),
        login = LoginUseCase(repository),
        logout = LogoutUseCase(repository),
        getCurrentUser = GetCurrentUserUseCase(repository)
    )
}