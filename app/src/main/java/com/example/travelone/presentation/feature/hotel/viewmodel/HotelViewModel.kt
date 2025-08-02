package com.example.travelone.presentation.feature.hotel.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelone.domain.model.hotel.Hotel
import com.example.travelone.domain.usecase.hotel.GetAllHotelsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelViewModel @Inject constructor(
    private val getAllHotelsUseCase: GetAllHotelsUseCase
): ViewModel() {

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    var hotels by mutableStateOf<List<Hotel>>(emptyList())
        private set

    init {
        loadHotels()
    }

    fun loadHotels() {
        viewModelScope.launch {
            _isLoading.value = true
            hotels = getAllHotelsUseCase()
            _isLoading.value = false
        }
    }
}