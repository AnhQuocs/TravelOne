package com.example.travelone.data.model.review

data class ReviewDto(
    val userId: String? = null,
    val userName: String? = null,
    val userProfilePicture: String? = null,
    val serviceId: String? = null,
    val serviceType: String? = null,
    val rating: Int? = 0,
    val comment: String? = null,
    val timestamp: String? = null
)