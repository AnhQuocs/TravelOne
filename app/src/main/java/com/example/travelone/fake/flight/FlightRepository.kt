package com.example.travelone.fake.flight

interface FlightRepository {
    suspend fun getAllFlights(): List<Flight>
}