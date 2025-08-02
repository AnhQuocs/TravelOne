package com.example.travelone.data.model.room

data class RoomDto(
    val hotelId: String? = null,
    val roomType: Map<String, String>? = null,
    val capacity: Int? = null,
    val pricePerNight: Int? = null,
    val description: Map<String, String>? = null,
    val imageUrl: String? = null,
    val numberOfBeds: Int? = null,
    val bedType: Map<String, String>? = null,
    val status: Map<String, String>? = null
)