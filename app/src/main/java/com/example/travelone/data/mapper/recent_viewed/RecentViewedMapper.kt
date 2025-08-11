package com.example.travelone.data.mapper.recent_viewed

import com.example.travelone.data.model.recent_viewed.RecentViewedDto
import com.example.travelone.domain.model.recent_viewed.RecentViewed
import com.example.travelone.domain.model.recent_viewed.ViewedType

fun RecentViewedDto.toDomain() = RecentViewed (
    id = id,
    type = ViewedType.valueOf(type.uppercase()),
    viewedAt = viewedAt
)

fun RecentViewed.toDto() = RecentViewedDto (
    id = id,
    type = type.name.lowercase(),
    viewedAt = viewedAt
)