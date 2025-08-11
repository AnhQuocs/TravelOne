package com.example.travelone.domain.repository.recent_viewed

import com.example.travelone.domain.model.recent_viewed.RecentViewed

interface RecentViewedRepository {
    suspend fun addRecentViewed(userId: String, recent: RecentViewed)
    suspend fun getRecentViewed(userId: String): List<RecentViewed>
    suspend fun clearRecentViewed(userId: String)
}