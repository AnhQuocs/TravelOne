package com.example.travelone.presentation.feature.search.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelone.domain.model.search.SearchResultItem
import com.example.travelone.domain.usecase.search.UnifiedSearchUseCase
import com.example.travelone.domain.usecase.search.suggestions.SearchSuggestionItem
import com.example.travelone.domain.usecase.search.suggestions.UnifiedSuggestionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UnifiedSearchViewModel @Inject constructor(
    private val unifiedSearchUseCase: UnifiedSearchUseCase,
    private val unifiedSuggestionUseCase: UnifiedSuggestionUseCase
) : ViewModel() {

    var searchResults by mutableStateOf<List<SearchResultItem>>(emptyList())
        private set

    var suggestions by mutableStateOf<List<SearchSuggestionItem>>(emptyList())
        private set

    var query by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var showSuggestions by mutableStateOf(false)
        private set

    fun onQueryChanged(newQuery: String) {
        query = newQuery
        showSuggestions = newQuery.isNotBlank()

        viewModelScope.launch {
            suggestions = if (newQuery.isBlank()) {
                searchResults = emptyList()
                suggestions = emptyList()
                emptyList()
            } else {
                unifiedSuggestionUseCase(newQuery)
            }
        }
    }

    fun onSearch() {
        viewModelScope.launch {
            isLoading = true
            searchResults = unifiedSearchUseCase(query)
            isLoading = false
        }
    }

    fun onSuggestionClicked(suggestion: SearchSuggestionItem) {
        showSuggestions = false

        when (suggestion) {
            is SearchSuggestionItem.HotelSuggestion -> {
                query = suggestion.name
            }
            is SearchSuggestionItem.FlightSuggestion -> {
                val departureMain = suggestion.flight.departureShortAddress
                    .split(",")
                    .firstOrNull()
                    ?.trim()
                    ?: ""

                val arrivalMain = suggestion.flight.arrivalShortAddress
                    .split(",")
                    .firstOrNull()
                    ?.trim()
                    ?: ""

                query = if (arrivalMain.isNotEmpty()) {
                    "$departureMain - $arrivalMain"
                } else {
                    departureMain
                }

                searchResults = listOf(SearchResultItem.FlightItem(suggestion.flight))
            }
        }

        suggestions = emptyList()

        viewModelScope.launch {
            isLoading = true
            if (suggestion is SearchSuggestionItem.HotelSuggestion) {
                searchResults = unifiedSearchUseCase(query)
            }
            isLoading = false
        }
    }
}