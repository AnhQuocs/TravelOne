package com.example.travelone.domain.usecase.hotel

import com.example.travelone.domain.model.hotel.Hotel
import com.example.travelone.domain.repository.hotel.HotelRepository

class SearchHotelsUseCase(
    private val repository: HotelRepository
) {
    suspend operator fun invoke(query: String): List<Hotel> {
        val keyword = query.trim().lowercase()
        val tokens = keyword.split(" ")

        return repository.getAllHotels().filter { hotel ->
            val name = hotel.name.lowercase()
            val shortAddress = hotel.shortAddress.lowercase()

            tokens.all { token ->
                name.contains(token) || shortAddress.contains(token)
            }
        }
    }
}