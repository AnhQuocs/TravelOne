package com.example.travelone.presentation.feature.hotel.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelone.domain.model.hotel.Hotel
import com.example.travelone.domain.usecase.hotel.GetHotelSuggestionsUseCase
import com.example.travelone.domain.usecase.hotel.SearchHotelsUseCase
import com.example.travelone.domain.usecase.search.suggestions.SearchSuggestionItem
import com.example.travelone.domain.usecase.search.suggestions.UnifiedSuggestionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelSearchViewModel @Inject constructor(
    private val searchHotelsUseCase: SearchHotelsUseCase,
    private val unifiedSuggestionUseCase: UnifiedSuggestionUseCase
) : ViewModel() {
    var searchResults by mutableStateOf<List<Hotel>>(emptyList())
        private set

    var suggestions by mutableStateOf<List<SearchSuggestionItem>>(emptyList())
        private set

    private val _islLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _islLoading

    fun onSearch(query: String) {
        viewModelScope.launch {
            _islLoading.value = true
            searchResults = searchHotelsUseCase(query)
            _islLoading.value = false
        }
    }

    fun onQueryChanged(input: String) {
        viewModelScope.launch {
            suggestions = if (input.isBlank()) emptyList()
            else unifiedSuggestionUseCase(input)
        }
    }
}