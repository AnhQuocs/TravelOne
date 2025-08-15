package com.example.travelone.data.mapper.hotel

import com.example.travelone.data.model.hotel.HotelDto
import com.example.travelone.domain.model.hotel.Hotel
import com.example.travelone.utils.LangUtils

fun HotelDto.toHotel(id: String): Hotel {
    return Hotel(
        id = id,
        name = LangUtils.getLocalizedText(name),
        description = LangUtils.getLocalizedText(description),
        amenities = LangUtils.getLocalizedList(amenities),
        address = address.orEmpty(),
        shortAddress = shortAddress.orEmpty(),
        city = city.orEmpty(),
        country = country.orEmpty(),
        thumbnailUrl = thumbnailUrl.orEmpty(),
        pricePerNightMin = pricePerNightMin ?: 0,
        averageRating = averageRating ?: 0.0,
        numberOfReviews = numberOfReviews ?: 0,
        latitude = latitude ?: 0.0,
        longitude = longitude ?: 0.0,
        checkInTime = checkInTime.orEmpty(),
        checkOutTime = checkOutTime.orEmpty()
    )
}