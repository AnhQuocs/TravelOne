package com.example.travelone.presentation.feature.main

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.travelone.R
import com.example.travelone.domain.model.hotel.Hotel
import com.example.travelone.domain.model.language.AppLanguage
import com.example.travelone.presentation.feature.hotel.viewmodel.HotelViewModel
import com.example.travelone.presentation.feature.room.viewmodel.RoomUiState
import com.example.travelone.presentation.feature.room.viewmodel.RoomViewModel
import com.example.travelone.presentation.language.LanguageViewModel
import com.example.travelone.utils.LangUtils

@Composable
fun MainScreenTest(
    navController: NavHostController,
    languageViewModel: LanguageViewModel = hiltViewModel(),
    hotelViewModel: HotelViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context as? ComponentActivity
    val currentLang by languageViewModel.currentLanguage.collectAsState()

    var lastLang by remember { mutableStateOf(currentLang) }

    val isLoading by hotelViewModel.isLoading
    val hotels = hotelViewModel.hotels

    // Khi currentLang thay đổi thì recreate
    LaunchedEffect(currentLang) {
        if (currentLang != lastLang) {
            lastLang = currentLang
            activity?.recreate()
        }
    }

    LaunchedEffect(Unit) {
        hotelViewModel.loadHotels()
    }

    Column(modifier = Modifier
        .padding(16.dp)
        .padding(top = 48.dp)) {
        Button(onClick = {
            LangUtils.currentLang = AppLanguage.ENGLISH.code
            languageViewModel.changeLanguage(AppLanguage.ENGLISH)
        }) {
            Text("English 🇬🇧")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            LangUtils.currentLang = AppLanguage.VIETNAMESE.code
            languageViewModel.changeLanguage(AppLanguage.VIETNAMESE)
        }) {
            Text("Tiếng Việt 🇻🇳")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.welcome))

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(hotels) { hotel ->
                    HotelCardTest(
                        hotel = hotel,
                        onClick = {
                            navController.navigate("roomList/${hotel.id}")
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun HotelCardTest(
    hotel: Hotel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = hotel.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = hotel.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "📍 ${hotel.city}, ${hotel.country}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun RoomListTest(
    hotelId: String,
    roomViewModel: RoomViewModel = hiltViewModel()
) {
    val state = roomViewModel.roomListState

    LaunchedEffect(hotelId) {
        roomViewModel.loadRooms(hotelId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 36.dp)
    ) {
        when (state) {
            is RoomUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is RoomUiState.Success -> {
                LazyColumn {
                    items(state.rooms) { room ->
                        Column(modifier = Modifier.padding(bottom = 16.dp)) {
                            Text(text = "Room type: ${room.roomType}")
                            Text(text = "Capacity: ${room.capacity}")
                            Text(text = "Price/Night: ${room.pricePerNight}")
                            Text(text = "Description: ${room.description}")
                            AsyncImage(
                                model = room.imageUrl,
                                contentDescription = null,
                                modifier = Modifier.size(200.dp),
                                placeholder = painterResource(id = R.drawable.placeholder)
                            )
                            Text(text = "Beds: ${room.numberOfBeds} - ${room.bedType}")
                            Text(text = "Status: ${room.status}")
                        }
                    }
                }
            }

            is RoomUiState.Error -> {
                Text("Lỗi: ${state.message}")
            }
        }
    }
}