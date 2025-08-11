package com.example.travelone.domain.usecase.flight

import com.example.travelone.domain.model.flight.Flight
import com.example.travelone.domain.repository.flight.FlightRepository

class GetAllFlightsUseCase (
    private val repository: FlightRepository
) {
    suspend operator fun invoke(): List<Flight> {
        return repository.getAllFlights()
    }
}