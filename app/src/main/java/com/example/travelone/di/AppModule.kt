package com.example.travelone.di

import android.content.Context
import com.example.travelone.data.preferences.language.LanguagePreferenceManager
import com.example.travelone.data.remote.api.IApiService
import com.example.travelone.data.repository.auth.AuthRepositoryImpl
import com.example.travelone.data.repository.hotel.HotelRepositoryImpl
import com.example.travelone.data.repository.location.LocationRepositoryImpl
import com.example.travelone.data.repository.room.RoomRepositoryImpl
import com.example.travelone.data.repository.weather.WeatherRepositoryImpl
import com.example.travelone.data.source.FirebaseHotelDataSource
import com.example.travelone.data.source.FirebaseRoomDataSource
import com.example.travelone.domain.repository.auth.AuthRepository
import com.example.travelone.domain.repository.hotel.HotelRepository
import com.example.travelone.domain.repository.location.LocationRepository
import com.example.travelone.domain.repository.room.RoomRepository
import com.example.travelone.domain.repository.weather.WeatherRepository
import com.example.travelone.domain.usecase.auth.AuthUseCase
import com.example.travelone.domain.usecase.auth.GetCurrentUserUseCase
import com.example.travelone.domain.usecase.auth.LoginUseCase
import com.example.travelone.domain.usecase.auth.LogoutUseCase
import com.example.travelone.domain.usecase.auth.SignUpUseCase
import com.example.travelone.domain.usecase.hotel.GetAllHotelsUseCase
import com.example.travelone.domain.usecase.hotel.GetRecommendedHotelsUseCase
import com.example.travelone.domain.usecase.location.GetUserLocationUseCase
import com.example.travelone.domain.usecase.room.GetRoomByIdUseCase
import com.example.travelone.domain.usecase.room.GetRoomsByHotelIdUseCase
import com.example.travelone.domain.usecase.weather.GetWeatherByLocationUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFireStore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
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
    fun provideGetRecommendedHotelsUseCase(repository: HotelRepository): GetRecommendedHotelsUseCase {
        return GetRecommendedHotelsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFirebaseHotelDataSource(): FirebaseHotelDataSource {
        return FirebaseHotelDataSource()
    }

    // ROOMS
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

    // WEATHER
    @Provides
    @Singleton
    fun provideApiService(): IApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: IApiService): WeatherRepository {
        return WeatherRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideWeatherUseCase(repository: WeatherRepository): GetWeatherByLocationUseCase {
        return GetWeatherByLocationUseCase(repository)
    }

    // Map
    @Provides
    @Singleton
    fun provideLocationProvider(@ApplicationContext context: Context): LocationRepository =
        LocationRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideGetUserLocationUseCase(locationProvider: LocationRepository): GetUserLocationUseCase =
        GetUserLocationUseCase(locationProvider)
}