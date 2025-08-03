package com.example.travelone.data.remote.api

import com.example.travelone.constant.Const.openWeatherMapApiKey
import com.example.travelone.domain.model.weather.WeatherResult
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric",
        @Query("appid") appId: String = openWeatherMapApiKey
    ): WeatherResult
}