package com.example.travelone.domain.usecase.recent_viewed

import com.example.travelone.domain.model.recent_viewed.RecentViewed
import com.example.travelone.domain.repository.recent_viewed.RecentViewedRepository

class GetRecentViewedUseCase(
    private val repository: RecentViewedRepository
) {
    suspend operator fun invoke(userId: String): List<RecentViewed> {
        return repository.getRecentViewed(userId)
    }
}