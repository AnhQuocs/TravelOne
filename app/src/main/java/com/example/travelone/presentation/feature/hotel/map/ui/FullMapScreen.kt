package com.example.travelone.presentation.feature.hotel.map.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.travelone.R
import com.example.travelone.domain.model.hotel.Hotel
import com.example.travelone.presentation.feature.hotel.viewmodel.HotelViewModel
import com.example.travelone.ui.theme.Dimens
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@Composable
fun FullMapScreen(
    navHostController: NavHostController,
    latLng: LatLng,
    hotelViewModel: HotelViewModel = hiltViewModel()
) {
    val hotels = hotelViewModel.hotels
    val context = LocalContext.current

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 14f)
    }

    val hotelBitmap = remember {
        bitmapFromVector(context, R.drawable.ic_hotel_marker, 80)
    }

    var selectedHotel by remember { mutableStateOf<Hotel?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        hotelViewModel.loadHotels()
    }

    LaunchedEffect(latLng) {
        cameraPositionState.animate(
            CameraUpdateFactory.newLatLngZoom(latLng, 3.5f)
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            cameraPositionState = cameraPositionState,
            modifier = Modifier.fillMaxSize()
        ) {
            Marker(
                state = MarkerState(position = latLng),
                title = "Your Location",
                snippet = null
            )

            hotels.forEach { hotel ->
                val pos = LatLng(hotel.latitude, hotel.longitude)
                Marker(
                    state = MarkerState(position = pos),
                    title = hotel.name,
                    icon = BitmapDescriptorFactory.fromBitmap(hotelBitmap),
                    onClick = {
                        selectedHotel = hotel

                        coroutineScope.launch {
                            cameraPositionState.animate(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(hotel.latitude, hotel.longitude),
                                    6f
                                )
                            )
                        }

                        true
                    }
                )
            }
        }

        MapTopBar(
            onBackClick = {
                navHostController.popBackStack()
            },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .zIndex(1f)
                .padding(top = Dimens.PaddingUltra)
        )

        selectedHotel?.let { hotel ->
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = hotel.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = hotel.shortAddress, style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Rating: ${hotel.averageRating}", style = MaterialTheme.typography.bodySmall)
                    Button(
                        onClick = {  },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("View Details")
                    }
                }

                IconButton(
                    onClick = {
                        selectedHotel = null

                        coroutineScope.launch {
                            cameraPositionState.animate(
                                CameraUpdateFactory.newLatLngZoom(latLng, 3f)
                            )
                        }
                    }
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Đóng")
                }
            }
        }
    }
}

fun bitmapFromVector(context: Context, vectorResId: Int, size: Int): Bitmap {
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        ?: throw IllegalArgumentException("Drawable not found")
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
    vectorDrawable.draw(canvas)
    return bitmap
}