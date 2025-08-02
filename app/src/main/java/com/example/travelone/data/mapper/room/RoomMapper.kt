package com.example.travelone.data.mapper.room

import com.example.travelone.data.model.room.RoomDto
import com.example.travelone.domain.model.room.Room
import com.example.travelone.utils.LangUtils

fun RoomDto.toRoom(id: String): Room {
    return Room (
        id = id,
        hotelId = hotelId.orEmpty(),
        roomType = LangUtils.getLocalizedText(roomType),
        capacity = capacity ?: 0,
        pricePerNight = pricePerNight ?: 0,
        description = LangUtils.getLocalizedText(description),
        imageUrl = imageUrl.orEmpty(),
        numberOfBeds = numberOfBeds ?: 0,
        bedType = LangUtils.getLocalizedText(bedType),
        status = LangUtils.getLocalizedText(status)
    )
}