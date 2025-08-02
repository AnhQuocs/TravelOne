package com.example.travelone.presentation.feature.room.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelone.domain.model.room.Room
import com.example.travelone.domain.usecase.room.GetRoomByIdUseCase
import com.example.travelone.domain.usecase.room.GetRoomsByHotelIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RoomUiState {
    object Loading : RoomUiState()
    data class Success(val rooms: List<Room>) : RoomUiState()
    data class Error(val message: String) : RoomUiState()
}

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val getRoomsByHotelId: GetRoomsByHotelIdUseCase,
    private val getRoomById: GetRoomByIdUseCase
) : ViewModel() {

    var roomListState by mutableStateOf<RoomUiState>(RoomUiState.Loading)
        private set

    var selectedRoom by mutableStateOf<Room?>(null)
        private set

    fun loadRooms(hotelId: String) {
        roomListState = RoomUiState.Loading
        viewModelScope.launch {
            try {
                val rooms = getRoomsByHotelId(hotelId)
                roomListState = RoomUiState.Success(rooms)
            } catch (e: Exception) {
                roomListState = RoomUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun loadRoom(roomId: String) {
        viewModelScope.launch {
            try {
                selectedRoom = getRoomById(roomId)
            } catch (e: Exception) {
                selectedRoom = null
            }
        }
    }
}