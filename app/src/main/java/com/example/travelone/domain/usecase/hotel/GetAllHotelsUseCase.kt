package com.example.travelone.domain.usecase.hotel

import com.example.travelone.domain.model.hotel.Hotel
import com.example.travelone.domain.repository.hotel.HotelRepository

class GetAllHotelsUseCase (
    private val repository: HotelRepository
) {
    suspend operator fun invoke(): List<Hotel> {
        return repository.getAllHotels()
    }
}