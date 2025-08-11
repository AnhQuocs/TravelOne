package com.example.travelone.domain.usecase.search

import com.example.travelone.domain.model.search.SearchResultItem
import com.example.travelone.domain.usecase.flight.SearchFlightsUseCase
import com.example.travelone.domain.usecase.hotel.SearchHotelsUseCase
import javax.inject.Inject

class UnifiedSearchUseCase @Inject constructor(
    private val hotelSearch: SearchHotelsUseCase,
    private val flightSearch: SearchFlightsUseCase
) {
    suspend operator fun invoke(query: String): List<SearchResultItem> {
        val hotels = hotelSearch(query).map { SearchResultItem.HotelItem(it) }
        val flights = flightSearch(query).map { SearchResultItem.FlightItem(it) }
        return hotels + flights
    }
}