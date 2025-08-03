package com.example.travelone.data.repository.weather

import com.example.travelone.data.remote.api.IApiService
import com.example.travelone.domain.model.weather.WeatherResult
import com.example.travelone.domain.repository.weather.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: IApiService
): WeatherRepository {
    override suspend fun getWeather(lat: Double, lon: Double): WeatherResult {
        return api.getWeather(lat, lon)
    }
}