package com.example.travelone.data.mapper.search_history

import com.example.travelone.data.model.search_history.SearchHistoryDto
import com.example.travelone.domain.model.search_history.SearchHistory

fun SearchHistoryDto.toDomain() = SearchHistory (
    id = id,
    title = title,
    subTitle = subTitle,
    historyAt = historyAt
)

fun SearchHistory.toDto() = SearchHistoryDto (
    id = id,
    title = title,
    subTitle = subTitle,
    historyAt = historyAt
)