package com.example.travelone.domain.model.hotel

data class Hotel(
    val id: String,
    val name: String,
    val description: String,
    val amenities: List<String>,
    val address: String,
    val shortAddress: String,
    val city: String,
    val country: String,
    val thumbnailUrl: String,
    val imageUrl: List<String>,
    val pricePerNightMin: Int,
    val averageRating: Double,
    val numberOfReviews: Int,
    val latitude: Double,
    val longitude: Double,
    val checkInTime: String,
    val checkOutTime: String
)