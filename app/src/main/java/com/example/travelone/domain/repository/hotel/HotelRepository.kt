package com.example.travelone.domain.repository.hotel

import com.example.travelone.domain.model.hotel.Hotel

interface HotelRepository {
    suspend fun getAllHotels(): List<Hotel>
}