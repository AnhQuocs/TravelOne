package com.example.travelone.domain.usecase.hotel

import com.example.travelone.domain.model.hotel.Hotel
import com.example.travelone.domain.repository.hotel.HotelRepository

class GetHotelByIdUseCase(
    private val repository: HotelRepository
) {
    suspend operator fun invoke(hotelId: String): Hotel? {
        return repository.getHotelById(hotelId)
    }
}