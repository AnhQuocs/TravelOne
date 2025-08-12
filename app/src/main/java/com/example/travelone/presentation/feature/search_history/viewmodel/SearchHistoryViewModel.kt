package com.example.travelone.presentation.feature.search_history.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelone.domain.model.search_history.SearchHistory
import com.example.travelone.domain.usecase.search_history.AddSearchHistoryUseCase
import com.example.travelone.domain.usecase.search_history.ClearAllSearchHistoryUseCase
import com.example.travelone.domain.usecase.search_history.GetSearchHistoryUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchHistoryViewModel @Inject constructor(
    private val addSearchHistoryUseCase: AddSearchHistoryUseCase,
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val clearAllSearchHistoryUseCase: ClearAllSearchHistoryUseCase
) : ViewModel() {

    var historyList by mutableStateOf<List<SearchHistory>>(emptyList())
        private set

    var isClearing by mutableStateOf(false)
        private set

    init {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if(userId != null) {
            loadHistory(userId)
        }
    }

    private fun loadHistory(userId: String) {
        viewModelScope.launch {
            historyList = getSearchHistoryUseCase(userId)
        }
    }

    fun addHistory(id: String, title: String, subTitle: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModelScope.launch {
            val history = SearchHistory(
                id = id,
                title = title,
                subTitle = subTitle,
                historyAt = System.currentTimeMillis()
            )

            addSearchHistoryUseCase(userId, history)
            loadHistory(userId)
        }
    }

    fun clearAllHistory() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModelScope.launch {
            isClearing = true
            historyList = emptyList()
            clearAllSearchHistoryUseCase(userId)
            loadHistory(userId)
            isClearing = false
        }
    }
}