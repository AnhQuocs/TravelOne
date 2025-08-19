package com.example.travelone.presentation.feature.room.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.travelone.MyApplication
import com.example.travelone.domain.model.room.Room
import com.example.travelone.domain.usecase.room.GetRoomByIdUseCase
import com.example.travelone.domain.usecase.room.GetRoomsByHotelIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val getRoomsByHotelId: GetRoomsByHotelIdUseCase
) : ViewModel() {

    private val _isRoomLoading = mutableStateOf(true)
    val isRoomLoading: State<Boolean> = _isRoomLoading

    var rooms by mutableStateOf<List<Room>>(emptyList())
        private set

    fun loadRooms(hotelId: String) {
        viewModelScope.launch {
            _isRoomLoading.value = true
            rooms = getRoomsByHotelId(hotelId)
            _isRoomLoading.value = false
        }
    }

    fun preloadImage(context: Context, url: String) {
        val request = ImageRequest.Builder(context)
            .data(url)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build()

        context.imageLoader.enqueue(request)
    }
}