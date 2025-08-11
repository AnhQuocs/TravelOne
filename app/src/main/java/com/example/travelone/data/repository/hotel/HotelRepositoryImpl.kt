package com.example.travelone.data.repository.hotel

import com.example.travelone.data.mapper.hotel.toHotel
import com.example.travelone.data.source.FirebaseHotelDataSource
import com.example.travelone.domain.model.hotel.Hotel
import com.example.travelone.domain.repository.hotel.HotelRepository

class HotelRepositoryImpl(
    private val dataSource: FirebaseHotelDataSource
): HotelRepository {
    override suspend fun getAllHotels(): List<Hotel> {
        return dataSource.fetchAllHotels().map { (id, dto) ->
            dto.toHotel(id)
        }
    }

    override suspend fun getRecommendHotels(minAverageRating: Double): List<Hotel> {
        return dataSource.fetchAllHotels()
            .map { (id, dto) ->
                dto.toHotel(id)
            }
            .filter { it.averageRating >= minAverageRating }
    }

    override suspend fun getHotelById(hotelId: String): Hotel? {
        val dto = dataSource.fetchHotelById(hotelId) ?: return null
        return dto.toHotel(hotelId)
    }
}