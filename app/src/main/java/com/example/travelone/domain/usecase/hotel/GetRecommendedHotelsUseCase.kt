package com.example.travelone.domain.usecase.hotel

import com.example.travelone.domain.model.hotel.Hotel
import com.example.travelone.domain.repository.hotel.HotelRepository

class GetRecommendedHotelsUseCase(
    private val repository: HotelRepository
) {
    suspend operator fun invoke(minAverageRating: Double = 4.7): List<Hotel> {
        return repository.getRecommendHotels(minAverageRating)
    }
}