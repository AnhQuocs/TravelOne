package com.example.travelone.domain.usecase.flight

import com.example.travelone.domain.model.flight.Flight
import com.example.travelone.domain.repository.flight.FlightRepository

class SearchFlightsUseCase(
    private val repository: FlightRepository
) {
    suspend operator fun invoke(query: String): List<Flight> {
        val keyword = query.trim().lowercase()
        val tokens = keyword.split(" ").filter { it.isNotBlank() }

        return repository.getAllFlights().filter { flight ->
            val airline = flight.airline.lowercase()
            val departureCode = flight.departureAirportCode.lowercase()
            val departureShort = flight.departureShortAddress.lowercase()
            val arrivalCode = flight.arrivalAirportCode.lowercase()
            val arrivalShort = flight.arrivalShortAddress.lowercase()

            tokens.all { token ->
                airline.contains(token) ||
                        departureCode.contains(token) ||
                        departureShort.contains(token) ||
                        arrivalCode.contains(token) ||
                        arrivalShort.contains(token)
            }
        }
    }
}