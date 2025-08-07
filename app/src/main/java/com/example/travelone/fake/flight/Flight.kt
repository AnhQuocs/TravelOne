package com.example.travelone.fake.flight

data class Flight(
    val id: String,
    val airline: String,
    val departureCity: String,
    val destinationCity: String,
    val departureTime: String,
    val arrivalTime: String,
    val price: Double
)
