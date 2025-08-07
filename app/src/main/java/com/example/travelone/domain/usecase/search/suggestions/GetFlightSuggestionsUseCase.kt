package com.example.travelone.domain.usecase.search.suggestions

import com.example.travelone.fake.flight.Flight
import com.example.travelone.fake.flight.FlightRepository

class GetFlightSuggestionsUseCase(
    private val repository: FlightRepository
) {
    suspend operator fun invoke(query: String): List<Flight> {
        val keyword = query.trim().lowercase()

        return repository.getAllFlights().filter {
            it.departureCity.lowercase().contains(keyword) ||
                    it.destinationCity.lowercase().contains(keyword)
        }
    }
}