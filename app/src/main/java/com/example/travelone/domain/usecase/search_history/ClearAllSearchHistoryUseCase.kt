package com.example.travelone.domain.usecase.search_history

import com.example.travelone.domain.repository.search_history.SearchHistoryRepository

class ClearAllSearchHistoryUseCase (
    private val repository: SearchHistoryRepository
) {
    suspend operator fun invoke(userId: String) {
        return repository.clearAllSearchHistory(userId)
    }
}