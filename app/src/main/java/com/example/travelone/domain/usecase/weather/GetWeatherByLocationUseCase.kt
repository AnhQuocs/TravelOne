package com.example.travelone.domain.usecase.weather

import com.example.travelone.domain.repository.weather.WeatherRepository

class GetWeatherByLocationUseCase (
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double) = repository.getWeather(lat, lon)
}