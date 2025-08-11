package com.example.travelone.presentation.feature.hotel.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.travelone.R
import com.example.travelone.domain.model.hotel.Hotel
import com.example.travelone.domain.model.recent_viewed.ViewedType
import com.example.travelone.presentation.components.TitleSection
import com.example.travelone.presentation.feature.hotel.viewmodel.HotelViewModel
import com.example.travelone.presentation.feature.recent_viewed.viewmodel.RecentViewedViewModel
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.JostTypography

@Composable
fun HotelList(
    hotelViewModel: HotelViewModel = hiltViewModel(),
    recentViewedViewModel: RecentViewedViewModel = hiltViewModel()
) {
    val hotels = hotelViewModel.hotels

    var isShowCardLoading by remember { mutableStateOf(false) }

    LaunchedEffect(hotels) {
        isShowCardLoading = hotels.isEmpty()
    }

    if(isShowCardLoading || hotels.isEmpty()) {
        MostPopularShimmerLoading()

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(AppSpacing.MediumLarge),
            contentPadding = PaddingValues(0.dp)
        ) {
            items(5) {
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(AppShape.ExtraLargeShape),
                    modifier = Modifier
                        .padding(top = Dimens.PaddingM)
                        .height(Dimens.HeightXXL)
                        .width(Dimens.WidthXL),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    HotelCardShimmerLoading()
                }
            }
        }
    } else {
        TitleSection(
            text1 = stringResource(id = R.string.most_popular),
            text2 = stringResource(id = R.string.see_all)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(AppSpacing.MediumLarge),
            contentPadding = PaddingValues(0.dp)
        ) {
            items(hotels, key = { it.id }) { hotel ->
                HotelCard(
                    hotel = hotel,
                    onClick = {
                        recentViewedViewModel.addRecent(hotel.id, ViewedType.HOTEL)
                    }
                )
            }
        }
    }
}

@Composable
fun HotelCard(hotel: Hotel, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(AppShape.ExtraLargeShape),
        modifier = Modifier
            .padding(top = Dimens.PaddingM)
            .height(Dimens.HeightXXL)
            .width(Dimens.WidthXL)
            .clickable { onClick() }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = hotel.thumbnailUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.PaddingS)
                    .padding(bottom = Dimens.PaddingS)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = hotel.name,
                    style = JostTypography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(AppSpacing.MediumPlus))

                Text(
                    text = hotel.shortAddress,
                    style = JostTypography.bodyMedium,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(AppSpacing.MediumPlus))

                Text(
                    text = "$" + hotel.pricePerNightMin.toString() + "/" + stringResource(id = R.string.night),
                    style = JostTypography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
            }

            Text(
                text = "⭐" + hotel.averageRating,
                style = JostTypography.bodyMedium,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = Dimens.PaddingM, bottom = Dimens.PaddingM)
            )

            Box(
                modifier = Modifier
                    .padding(top = Dimens.PaddingSM, end = Dimens.PaddingSM)
                    .align(Alignment.TopEnd)
                    .size(Dimens.SizeL)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {

                IconButton(
                    onClick = { },
                    modifier = Modifier.size(Dimens.SizeSM)
                ) {
                    Icon(
                        Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }
        }
    }
}