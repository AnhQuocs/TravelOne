package com.example.travelone.presentation.feature.flight.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelone.domain.model.flight.Flight
import com.example.travelone.domain.usecase.flight.GetAllFlightsUseCase
import com.example.travelone.domain.usecase.flight.GetFlightByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightViewModel @Inject constructor(
    private val getAllFlightsUseCase: GetAllFlightsUseCase,
    private val getFlightByIdUseCase: GetFlightByIdUseCase
): ViewModel() {

    private val _isFlightLoading = mutableStateOf(true)
    val isFlightLoading: State<Boolean> = _isFlightLoading

    var flights by mutableStateOf<List<Flight>>(emptyList())
        private set

    var flightDetails by mutableStateOf<Map<String, Flight>>(emptyMap())
        private set

    fun loadFlights() {
        viewModelScope.launch {
            _isFlightLoading.value = true
            flights = getAllFlightsUseCase()
            _isFlightLoading.value = false
        }
    }

    fun loadFLightById(flightId: String) {
        if(flightDetails.containsKey(flightId)) return

        viewModelScope.launch {
            val flight = getFlightByIdUseCase(flightId)
            flight?.let {
                flightDetails = flightDetails + (flightId to it)
            }
        }
    }
}