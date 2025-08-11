package com.example.travelone.domain.repository.flight

import com.example.travelone.domain.model.flight.Flight

interface FlightRepository {
    suspend fun getAllFlights(): List<Flight>
    suspend fun getFLightById(flightId: String): Flight?
}