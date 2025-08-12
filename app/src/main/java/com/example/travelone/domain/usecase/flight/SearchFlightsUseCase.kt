package com.example.travelone.domain.usecase.flight

import com.example.travelone.domain.model.flight.Flight
import com.example.travelone.domain.repository.flight.FlightRepository

class SearchFlightsUseCase(
    private val repository: FlightRepository
) {
    private fun normalizeText(text: String): String {
        return text.lowercase()
            .replace("→", "-")
            .replace("–", "-")
            .replace("—", "-")
            .replace("\\s+".toRegex(), " ")
            .trim()
    }

    suspend operator fun invoke(query: String): List<Flight> {
        val normalizedQuery = normalizeText(query)
        val tokens = normalizedQuery.split(" ").filter { it.isNotBlank() }

        return repository.getAllFlights().filter { flight ->
            val airline = normalizeText(flight.airline)
            val departureCode = normalizeText(flight.departureAirportCode)
            val departureShort = normalizeText(flight.departureShortAddress)
            val arrivalCode = normalizeText(flight.arrivalAirportCode)
            val arrivalShort = normalizeText(flight.arrivalShortAddress)

            val combinedRoute = "$departureShort - $arrivalShort"

            tokens.all { token ->
                airline.contains(token) ||
                        departureCode.contains(token) ||
                        departureShort.contains(token) ||
                        arrivalCode.contains(token) ||
                        arrivalShort.contains(token) ||
                        combinedRoute.contains(token)
            } || combinedRoute.contains(normalizedQuery)
        }
    }
}