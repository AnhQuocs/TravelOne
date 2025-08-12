package com.example.travelone.domain.usecase.search_history

import com.example.travelone.domain.model.search_history.SearchHistory
import com.example.travelone.domain.repository.search_history.SearchHistoryRepository

class GetSearchHistoryUseCase(
    private val repository: SearchHistoryRepository
) {
    suspend operator fun invoke(userId: String): List<SearchHistory> {
        return repository.getSearchHistory(userId)
    }
}