package com.example.travelone.domain.repository.room

import com.example.travelone.domain.model.room.Room

interface RoomRepository {
    suspend fun getRoomsByHotelId(hotelId: String): List<Room>
    suspend fun getRoomById(roomId: String): Room?
}