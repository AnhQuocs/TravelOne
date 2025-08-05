package com.example.travelone.domain.usecase.hotel

import com.example.travelone.domain.repository.hotel.HotelRepository

class GetHotelSuggestionsUseCase(
    private val repository: HotelRepository
) {
    suspend operator fun invoke(query: String): List<String> {
        val keyword = query.trim().lowercase()
        return repository.getAllHotels()
            .map { it.name }
            .filter { it.lowercase().contains(keyword) }
            .distinct()
    }
}