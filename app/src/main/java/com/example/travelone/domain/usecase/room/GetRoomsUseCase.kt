package com.example.travelone.domain.usecase.room

import com.example.travelone.domain.model.room.Room
import com.example.travelone.domain.repository.room.RoomRepository

class GetRoomsByHotelIdUseCase(
    private val repository: RoomRepository
) {
    suspend operator fun invoke(hotelId: String): List<Room> {
        return repository.getRoomsByHotelId(hotelId)
    }
}

class GetRoomByIdUseCase (
    private val repository: RoomRepository
) {
    suspend operator fun invoke(roomId: String): Room? {
        return repository.getRoomById(roomId)
    }
}