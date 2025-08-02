package com.example.travelone.di

import android.content.Context
import com.example.travelone.data.preferences.language.LanguagePreferenceManager
import com.example.travelone.data.repository.auth.AuthRepositoryImpl
import com.example.travelone.data.repository.hotel.HotelRepositoryImpl
import com.example.travelone.data.repository.room.RoomRepositoryImpl
import com.example.travelone.data.source.FirebaseHotelDataSource
import com.example.travelone.data.source.FirebaseRoomDataSource
import com.example.travelone.domain.repository.auth.AuthRepository
import com.example.travelone.domain.repository.hotel.HotelRepository
import com.example.travelone.domain.repository.room.RoomRepository
import com.example.travelone.domain.usecase.auth.AuthUseCase
import com.example.travelone.domain.usecase.auth.CheckUserLoggedInUseCase
import com.example.travelone.domain.usecase.auth.GetCurrentUserUseCase
import com.example.travelone.domain.usecase.auth.LoginUseCase
import com.example.travelone.domain.usecase.auth.LogoutUseCase
import com.example.travelone.domain.usecase.auth.SignUpUseCase
import com.example.travelone.domain.usecase.hotel.GetAllHotelsUseCase
import com.example.travelone.domain.usecase.room.GetRoomByIdUseCase
import com.example.travelone.domain.usecase.room.GetRoomsByHotelIdUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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

    @Provides
    @Singleton
    fun provideLanguagePreferenceManager(
        @ApplicationContext context: Context
    ): LanguagePreferenceManager = LanguagePreferenceManager(context)

    // HOTELS
    @Provides
    @Singleton
    fun provideHotelRepository(
        dataSource: FirebaseHotelDataSource
    ): HotelRepository {
        return HotelRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideGetAllHotelsUseCase(
        hotelRepository: HotelRepository
    ): GetAllHotelsUseCase {
        return GetAllHotelsUseCase(hotelRepository)
    }

    @Provides
    @Singleton
    fun provideFirebaseHotelDataSource(): FirebaseHotelDataSource {
        return FirebaseHotelDataSource()
    }

    //ROOMS
    @Provides
    @Singleton
    fun provideRoomRepository(
        dataSource: FirebaseRoomDataSource
    ): RoomRepository {
        return RoomRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideGetRoomsByHotelIdUseCase(
        roomRepository: RoomRepository
    ): GetRoomsByHotelIdUseCase {
        return GetRoomsByHotelIdUseCase(roomRepository)
    }

    @Provides
    @Singleton
    fun provideGetRoomByIdUseCase(
        roomRepository: RoomRepository
    ): GetRoomByIdUseCase {
        return GetRoomByIdUseCase(roomRepository)
    }

    @Provides
    @Singleton
    fun provideFirebaseRoomDataSource(): FirebaseRoomDataSource {
        return FirebaseRoomDataSource()
    }
}