package com.example.travelone.data.mapper.review

import com.example.travelone.data.model.review.ReviewDto
import com.example.travelone.domain.model.review.Review

fun ReviewDto.toReview(): Review {
    return Review(
        serviceId = serviceId.orEmpty(),
        userId = userId.orEmpty(),
        userName = userName.orEmpty(),
        userProfilePicture = userProfilePicture.orEmpty(),
        serviceType = serviceType.orEmpty(),
        rating = rating ?: 0,
        comment = comment.orEmpty(),
        timestamp = timestamp.orEmpty()
    )
}