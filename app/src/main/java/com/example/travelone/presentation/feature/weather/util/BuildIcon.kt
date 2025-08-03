package com.example.travelone.presentation.feature.weather.util

object BuildIcon {
    fun buildIcon(icon: String): String {
        return "https://openweathermap.org/img/wn/$icon@2x.png"
    }
}