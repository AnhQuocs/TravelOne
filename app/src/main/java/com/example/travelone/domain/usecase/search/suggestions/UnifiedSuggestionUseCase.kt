package com.example.travelone.domain.usecase.search.suggestions

import com.example.travelone.domain.usecase.hotel.GetHotelSuggestionsUseCase

class UnifiedSuggestionUseCase(
    private val getHotelSuggestionsUseCase: GetHotelSuggestionsUseCase,
    private val getFlightSuggestionsUseCase: GetFlightSuggestionsUseCase
) {
    suspend operator fun invoke(query: String): List<SearchSuggestionItem> {
        val hotelSuggestions = getHotelSuggestionsUseCase(query)
            .map { hotel ->
                SearchSuggestionItem.HotelSuggestion(
                    name = hotel.name,
                    shortAddress = hotel.shortAddress
                )
            }

        val flightSuggestions = getFlightSuggestionsUseCase(query)
            .map { SearchSuggestionItem.FlightSuggestion(it) }

        return hotelSuggestions + flightSuggestions
    }
}