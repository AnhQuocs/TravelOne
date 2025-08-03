package com.example.travelone.domain.repository.weather

import com.example.travelone.domain.model.weather.WeatherResult

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double): WeatherResult
}