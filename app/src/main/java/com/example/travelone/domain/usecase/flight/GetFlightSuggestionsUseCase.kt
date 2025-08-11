package com.example.travelone.domain.usecase.flight

import com.example.travelone.domain.model.flight.Flight
import com.example.travelone.domain.repository.flight.FlightRepository

class GetFlightSuggestionsUseCase(
    private val repository: FlightRepository
) {
    suspend operator fun invoke(query: String): List<Flight> {
        val tokens = query
            .trim()
            .lowercase()
            .split("\\s+".toRegex())
            .filter { it.isNotBlank() }

        return repository.getAllFlights().filter { flight ->
            val searchableFields = listOf(
                flight.airline.lowercase(),
                flight.departureAirportCode.lowercase(),
                flight.departureShortAddress.lowercase(),
                flight.arrivalAirportCode.lowercase(),
                flight.arrivalShortAddress.lowercase()
            )

            tokens.all { token ->
                searchableFields.any { field -> field.contains(token) }
            }
        }
    }

}