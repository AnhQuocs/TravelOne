package com.example.travelone.fake.flight

class SearchFlightsUseCase(
    private val repository: FlightRepository
) {
    suspend operator fun invoke(query: String): List<Flight> {
        val keyword = query.trim().lowercase()
        val tokens = keyword.split(" ")

        return repository.getAllFlights().filter { flight ->
            val airline = flight.airline.lowercase()
            val from = flight.departureCity.lowercase()
            val to = flight.destinationCity.lowercase()

            tokens.all { token ->
                airline.contains(token) || from.contains(token) || to.contains(token)
            }
        }
    }
}