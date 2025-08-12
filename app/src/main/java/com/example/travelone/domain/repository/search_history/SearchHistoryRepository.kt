package com.example.travelone.domain.repository.search_history

import com.example.travelone.domain.model.search_history.SearchHistory

interface SearchHistoryRepository {
    suspend fun addSearchHistory(userId: String, history: SearchHistory)
    suspend fun getSearchHistory(userId: String): List<SearchHistory>
    suspend fun clearAllSearchHistory(userId: String)
}