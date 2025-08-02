package com.example.travelone.domain.model.room

data class Room(
    val id: String,
    val hotelId: String,
    val roomType: String,
    val capacity: Int,
    val pricePerNight: Int,
    val description: String,
    val imageUrl: String,
    val numberOfBeds: Int,
    val bedType: String,
    val status: String
)