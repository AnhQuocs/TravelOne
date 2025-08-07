package com.example.travelone.domain.usecase.hotel

import com.example.travelone.domain.model.hotel.Hotel
import com.example.travelone.domain.repository.hotel.HotelRepository

class GetHotelSuggestionsUseCase(
    private val repository: HotelRepository
) {
    suspend operator fun invoke(query: String): List<Hotel> {
        val keyword = query.trim().lowercase()
        return repository.getAllHotels()
            .filter {
                it.name.lowercase().contains(keyword) || it.shortAddress.lowercase().contains(keyword)
            }
            .sortedBy {
                val inName = it.name.lowercase().contains(keyword)
                if (inName) 0 else 1
            }
            .distinctBy { it.name }
    }
}