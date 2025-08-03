package com.example.travelone.domain.model.weather

data class WeatherResult(
    val name: String?,
    val weather: List<Weather>?,
    val main: Main?,
    val dt: Long?
)

data class Weather (val icon: String?, val description: String?)
data class Main (val temp: Double?)