package com.example.travelone.domain.usecase.flight

import com.example.travelone.domain.model.flight.Flight
import com.example.travelone.domain.repository.flight.FlightRepository

class GetFlightByIdUseCase(
    private val repository: FlightRepository
) {
    suspend operator fun invoke(flightId: String): Flight? {
        return repository.getFLightById(flightId)
    }
}