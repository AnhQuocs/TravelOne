package com.example.travelone.presentation.feature.hotel.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.travelone.R
import com.example.travelone.domain.model.hotel.Hotel
import com.example.travelone.presentation.components.AppTopBar
import com.example.travelone.presentation.components.ReadMoreText
import com.example.travelone.presentation.components.TitleSection
import com.example.travelone.presentation.components.TopBarIcon
import com.example.travelone.presentation.feature.hotel.viewmodel.HotelViewModel
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.CloudyBlue
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.JostTypography
import com.example.travelone.ui.theme.OceanBlue

@Composable
fun HotelDetailSection(
    hotelId: String,
    navHostController: NavHostController,
    hotelViewModel: HotelViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        hotelViewModel.loadHotelById(hotelId)
    }

    val hotel = hotelViewModel.hotelDetails[hotelId]

    HotelDetailScreen(
        hotel = hotel,
        onBackClick = { navHostController.popBackStack() }
    )
}

@Composable
fun HotelDetailScreen(
    hotel: Hotel?,
    onBackClick: () -> Unit
) {
    val amenities = hotel?.amenities

    if(hotel != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                model = hotel.thumbnailUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Scaffold(
            topBar = {
                AppTopBar(
                    icon1 = TopBarIcon.Resource(R.drawable.ic_back),
                    text = stringResource(id = R.string.detail),
                    icon2 = TopBarIcon.Vector(Icons.Default.MoreVert),
                    onIcon1Click = { onBackClick() },
                    onIcon2Click = {},
                    color = Color.White
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f)
                        .align(Alignment.BottomCenter)
                        .clip(
                            RoundedCornerShape(
                                topStart = AppShape.ExtraExtraLargeShape,
                                topEnd = AppShape.ExtraExtraLargeShape
                            )
                        )
                        .background(color = Color.White),
                    contentPadding = PaddingValues(Dimens.PaddingM)
                ) {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = hotel.name,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.height(AppSpacing.Medium))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = OceanBlue,
                                    modifier = Modifier.size(Dimens.SizeSM)
                                )

                                Spacer(modifier = Modifier.width(AppSpacing.Small))

                                Text(
                                    text = hotel.shortAddress,
                                    color = Color.Black.copy(alpha = 0.5f),
                                    style = JostTypography.labelLarge
                                )

                                Spacer(modifier = Modifier.width(AppSpacing.Large))

                                Text(
                                    text = "⭐" + hotel.averageRating,
                                    color = Color.Black,
                                    style = JostTypography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
                                )
                            }
                        }
                    }

                    item {
                        if (amenities != null) {
                            Column(
                                modifier = Modifier.padding(vertical = Dimens.PaddingSM)
                            ) {
                                TitleSection(
                                    text1 = stringResource(id = R.string.amenities),
                                    text2 = stringResource(id = R.string.see_all)
                                )

                                Spacer(modifier = Modifier.height(AppSpacing.Medium))

                                AmenitySection(amenities)
                            }
                        } else {
                          // Loading
                        }
                    }

                    item {
                        Column {
                            TitleSection(
                                text1 = stringResource(id = R.string.description),
                                text2 = ""
                            )

                            Spacer(modifier = Modifier.height(AppSpacing.MediumPlus))

                            ReadMoreText(description = hotel.description)
                        }
                    }
                }
            }

        }
    } else {
        // Loading
    }
}

@Composable
fun AmenitySection(amenities: List<String>) {
    val amenityIcons = mapOf(
        "Free Wi-Fi" to R.drawable.ic_wifi,
        "Swimming Pool" to R.drawable.ic_swim,
        "Restaurant and Bar" to R.drawable.ic_restaurant,
        "Room Service" to R.drawable.ic_bed,
        "Free Parking" to R.drawable.ic_free_parking,
        "Gym and Spa" to R.drawable.ic_gym,
        "Breakfast Included" to R.drawable.ic_breakfast,
        "Ski Storage" to R.drawable.ic_ski_storage,
        "Hot Tub" to R.drawable.ic_hot_tub,
        "Restaurant" to R.drawable.ic_restaurant,
        "Outdoor Patio" to R.drawable.ic_outdoor_patio,
        "Spa and Wellness Center" to R.drawable.ic_spa,

        "Wi-Fi miễn phí" to R.drawable.ic_wifi,
        "Hồ bơi" to R.drawable.ic_swim,
        "Nhà hàng và quầy bar" to R.drawable.ic_restaurant,
        "Dịch vụ phòng" to R.drawable.ic_bed,
        "Bãi đậu xe miễn phí" to R.drawable.ic_free_parking,
        "Phòng gym và spa" to R.drawable.ic_gym,
        "Bữa sáng miễn phí" to R.drawable.ic_breakfast,
        "Kho trượt tuyết" to R.drawable.ic_ski_storage,
        "Bồn tắm nước nóng" to R.drawable.ic_hot_tub,
        "Nhà hàng" to R.drawable.ic_restaurant,
        "Sân vườn ngoài trời" to R.drawable.ic_outdoor_patio,
        "Spa & chăm sóc sức khỏe" to R.drawable.ic_spa
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        amenities.forEach { amenity ->
            val painter: Painter = amenityIcons[amenity]?.let { resId ->
                painterResource(id = resId)
            } ?: rememberVectorPainter(Icons.Default.Info)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(Dimens.SizeXXL)
                        .clip(CircleShape)
                        .background(CloudyBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier.size(Dimens.SizeM),
                        colorFilter = ColorFilter.tint(Color.Black.copy(0.6f))
                    )
                }

                Spacer(modifier = Modifier.height(AppSpacing.Small))

                Text(
                    text = amenity,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Normal),
                    color = Color.Black.copy(0.6f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}