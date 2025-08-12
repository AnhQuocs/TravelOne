package com.example.travelone.domain.usecase.search.suggestions

import com.example.travelone.domain.model.flight.Flight
import com.example.travelone.domain.model.hotel.Hotel

sealed class SearchSuggestionItem {
    data class HotelSuggestion(val hotel: Hotel, val name: String, val shortAddress: String) : SearchSuggestionItem()
    data class FlightSuggestion(val flight: Flight) : SearchSuggestionItem()
}