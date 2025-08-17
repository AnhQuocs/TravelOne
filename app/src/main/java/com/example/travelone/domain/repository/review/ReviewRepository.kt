package com.example.travelone.domain.repository.review

import com.example.travelone.domain.model.review.Review

interface ReviewRepository {
    suspend fun getReviewsByServiceId(serviceId: String): List<Review>
}