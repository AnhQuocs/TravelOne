package com.example.travelone.presentation.feature.hotel.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelone.domain.model.hotel.Hotel
import com.example.travelone.domain.usecase.hotel.GetAllHotelsUseCase
import com.example.travelone.domain.usecase.hotel.GetRecommendedHotelsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelViewModel @Inject constructor(
    private val getAllHotelsUseCase: GetAllHotelsUseCase,
    private val getRecommendedHotelsUseCase: GetRecommendedHotelsUseCase
): ViewModel() {

    private val _isHotelLoading = mutableStateOf(true)
    val isHotelLoading: State<Boolean> = _isHotelLoading

    private val _isRecommendedLoading = mutableStateOf(true)
    val isRecommendedLoading: State<Boolean> = _isRecommendedLoading

    var hotels by mutableStateOf<List<Hotel>>(emptyList())
        private set

    var recommendedHotels by mutableStateOf<List<Hotel>>(emptyList())
        private set

    fun loadHotels() {
        viewModelScope.launch {
            _isHotelLoading.value = true
            hotels = getAllHotelsUseCase()
            _isHotelLoading.value = false
        }
    }

    fun loadRecommendedHotels() {
        viewModelScope.launch {
            _isRecommendedLoading.value = true
            recommendedHotels = getRecommendedHotelsUseCase()
            _isRecommendedLoading.value = false
        }
    }
}