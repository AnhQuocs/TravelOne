package com.example.travelone.data.repository.room

import com.example.travelone.data.mapper.room.toRoom
import com.example.travelone.data.source.FirebaseRoomDataSource
import com.example.travelone.domain.model.room.Room
import com.example.travelone.domain.repository.room.RoomRepository

class RoomRepositoryImpl(
    private val dataSource: FirebaseRoomDataSource
) : RoomRepository {

    override suspend fun getRoomsByHotelId(hotelId: String): List<Room> {
        return dataSource.fetchRoomsByHotelId(hotelId)
            .map { (id, dto) -> dto.toRoom(id) }
    }

    override suspend fun getRoomById(roomId: String): Room? {
        return dataSource.fetchRoomById(roomId)?.toRoom(roomId)
    }
}