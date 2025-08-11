package com.example.travelone.domain.model.recent_viewed

data class RecentViewed(
    val id: String,
    val type: ViewedType,
    val viewedAt: Long
)

enum class ViewedType {
    HOTEL, FLIGHT
}