package com.example.travelone.presentation.feature.recent_viewed.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travelone.domain.model.recent_viewed.ViewedType
import com.example.travelone.presentation.components.AppLineGray
import com.example.travelone.presentation.components.HotelCardHorizontal
import com.example.travelone.presentation.feature.flight.ui.FlightCard
import com.example.travelone.presentation.feature.flight.viewmodel.FlightViewModel
import com.example.travelone.presentation.feature.hotel.ui.HotelCardShimmerLoading
import com.example.travelone.presentation.feature.hotel.ui.RecommendedCardShimmerLoading
import com.example.travelone.presentation.feature.hotel.viewmodel.HotelViewModel
import com.example.travelone.presentation.feature.recent_viewed.viewmodel.RecentViewedViewModel
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.Dimens

@Composable
fun RecentList(
    recentViewedViewModel: RecentViewedViewModel = hiltViewModel(),
    hotelViewModel: HotelViewModel = hiltViewModel(),
    flightViewModel: FlightViewModel = hiltViewModel(),
    onHotelClick: () -> Unit,
    onFlightClick: () -> Unit
) {
    val recentList = recentViewedViewModel.recentList

    if(recentList.isEmpty()) {
        Column {
            repeat(3) { index ->
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(AppShape.ExtraLargeShape),
                    modifier = Modifier
                        .height(Dimens.HeightXL)
                        .padding(bottom = Dimens.PaddingS, top = Dimens.PaddingXSPlus)
                ) {
                    RecommendedCardShimmerLoading()
                }

                Spacer(modifier = Modifier.height(AppSpacing.MediumPlus))

                if (index < 2) {
                    AppLineGray()
                }
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            recentList.take(3).forEach { item ->
                when (item.type) {
                    ViewedType.HOTEL -> {
                        val hotel = hotelViewModel.hotelDetails[item.id]
                        if (hotel != null) {
                            HotelCardHorizontal(hotel, onClick = onHotelClick)
                        } else {
                            LaunchedEffect(item.id) {
                                hotelViewModel.loadHotelById(item.id)
                            }
                            Spacer(modifier = Modifier.height(100.dp))
                        }
                    }
                    ViewedType.FLIGHT -> {
                        val flight = flightViewModel.flightDetails[item.id]
                        if (flight != null) {
                            FlightCard(flight, onClick = onFlightClick)
                        } else {
                            LaunchedEffect(item.id) {
                                flightViewModel.loadFLightById(item.id)
                            }
                            Spacer(modifier = Modifier.height(100.dp))
                        }
                    }
                }
            }
        }
    }
}