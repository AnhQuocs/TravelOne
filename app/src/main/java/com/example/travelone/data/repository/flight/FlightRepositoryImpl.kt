package com.example.travelone.data.repository.flight

import com.example.travelone.data.mapper.flight.toFlight
import com.example.travelone.data.source.FirebaseFlightDataSource
import com.example.travelone.domain.model.flight.Flight
import com.example.travelone.domain.repository.flight.FlightRepository

class FlightRepositoryImpl(
    private val dataSource: FirebaseFlightDataSource
): FlightRepository {
    override suspend fun getAllFlights(): List<Flight> {
        return dataSource.fetchAllFlights().map { (id, dto) ->
            dto.toFlight(id)
        }
    }

    override suspend fun getFLightById(flightId: String): Flight? {
        val dto = dataSource.fetchFlightById(flightId) ?: return null
        return dto.toFlight(flightId)
    }
}