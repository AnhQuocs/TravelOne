package com.example.travelone.domain.usecase.search.suggestions

import com.example.travelone.domain.model.flight.Flight

sealed class SearchSuggestionItem {
    data class HotelSuggestion(val name: String, val shortAddress: String) : SearchSuggestionItem()
    data class FlightSuggestion(val flight: Flight) : SearchSuggestionItem()
}