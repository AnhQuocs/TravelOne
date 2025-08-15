package com.example.travelone.data.model.hotel

data class HotelDto(
    val name: Map<String, String>? = null,
    val description: Map<String, String>? = null,
    val amenities: Map<String, List<String>>? = null,
    val address: String? = null,
    val shortAddress: String? = null,
    val city: String? = null,
    val country: String? = null,
    val thumbnailUrl: String? = null,
    val pricePerNightMin: Int? = null,
    val averageRating: Double? = null,
    val numberOfReviews: Int? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val checkInTime: String? = null,
    val checkOutTime: String? = null
)