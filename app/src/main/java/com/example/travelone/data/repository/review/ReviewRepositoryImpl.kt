package com.example.travelone.data.repository.review

import com.example.travelone.data.mapper.review.toReview
import com.example.travelone.data.source.FirebaseReviewDataSource
import com.example.travelone.domain.model.review.Review
import com.example.travelone.domain.repository.review.ReviewRepository

class ReviewRepositoryImpl (
    private val dataSource: FirebaseReviewDataSource
): ReviewRepository {

    override suspend fun getReviewsByServiceId(serviceId: String): List<Review> {
        val dtoList = dataSource.fetchReviewsByServiceId(serviceId)
        return dtoList.map { it.toReview() }
    }
}