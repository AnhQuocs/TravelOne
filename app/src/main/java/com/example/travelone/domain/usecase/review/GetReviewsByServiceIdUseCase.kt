package com.example.travelone.domain.usecase.review

import com.example.travelone.domain.model.review.Review
import com.example.travelone.domain.repository.review.ReviewRepository

class GetReviewsByServiceIdUseCase (
    private val repository: ReviewRepository
) {
    suspend operator fun invoke(serviceId: String): List<Review> {
        return repository.getReviewsByServiceId(serviceId)
    }
}