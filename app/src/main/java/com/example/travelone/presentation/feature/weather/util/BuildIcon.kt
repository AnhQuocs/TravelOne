package com.example.travelone.presentation.feature.weather.util

object BuildIcon {
    fun buildIcon(icon: String): String {
        return "http://openweathermap.org/img/wn/$icon@4x.png"
    }
}