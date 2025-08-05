package com.example.travelone.presentation.feature.hotel.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.travelone.R
import com.example.travelone.domain.model.hotel.Hotel
import com.example.travelone.presentation.components.AppLineGray
import com.example.travelone.presentation.feature.hotel.viewmodel.HotelViewModel
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.JostTypography
import com.example.travelone.ui.theme.OceanBlue
import com.example.travelone.ui.theme.PrimaryBlue

@Composable
fun HotelRecommendedList(hotelViewModel: HotelViewModel = hiltViewModel()) {
    val recommendedHotels = hotelViewModel.recommendedHotels

    val isShowCardLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        hotelViewModel.loadRecommendedHotels()
    }

    if(isShowCardLoading || recommendedHotels.isEmpty()) {
        RecommendedTextShimmerLoading()

        Spacer(modifier = Modifier.height(AppSpacing.MediumPlus))

        Column() {
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
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.recommended),
                    color = Color.Black,
                    style = JostTypography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )

                Text(
                    text = stringResource(id = R.string.see_all),
                    style = JostTypography.bodyMedium.copy(lineHeight = 0.sp),
                    color = PrimaryBlue,
                    modifier = Modifier.clickable {  }
                )
            }

            Spacer(modifier = Modifier.height(AppSpacing.MediumPlus))

            recommendedHotels.forEachIndexed { index, hotel ->
                HotelRecommendedItem(hotel)

                if (index < recommendedHotels.lastIndex) {
                    AppLineGray(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun HotelRecommendedItem(hotel: Hotel) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(AppShape.SmallShape),
        modifier = Modifier
            .height(Dimens.HeightXL)
            .padding(bottom = Dimens.PaddingS, top = Dimens.PaddingXSPlus)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.Top
            ) {
                AsyncImage(
                    model = hotel.thumbnailUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(AppShape.MediumShape)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(AppSpacing.Small))

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = hotel.name,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = JostTypography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.Black,
                        modifier = Modifier
                            .padding(start = Dimens.PaddingXS)
                            .fillMaxWidth()
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_location),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = Color.Black.copy(alpha = 0.4f)),
                            modifier = Modifier.size(Dimens.SizeM)
                        )

                        Text(
                            text = hotel.shortAddress,
                            style = JostTypography.labelLarge,
                            color = Color.Black.copy(alpha = 0.4f)
                        )
                    }

                    Text(
                        buildAnnotatedString {
                            withStyle(style = JostTypography.titleSmall.toSpanStyle().copy(color = OceanBlue, fontWeight = FontWeight.Bold)) {
                                append("$" + hotel.pricePerNightMin.toString())
                            }
                            withStyle(style = JostTypography.titleSmall.toSpanStyle().copy(color = Color.Black)) {
                                append("/" + stringResource(id = R.string.night))
                            }
                        },
                        modifier = Modifier.padding(start = Dimens.PaddingXS)
                    )
                }
            }

            Text(
                text = "⭐${hotel.averageRating}",
                style = JostTypography.bodyMedium,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.Top)
                    .padding(start = AppSpacing.Small)
            )
        }
    }
}