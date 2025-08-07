package com.example.travelone.presentation.feature.hotel.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travelone.R
import com.example.travelone.presentation.components.AppLineGray
import com.example.travelone.presentation.components.HotelCardHorizontal
import com.example.travelone.presentation.feature.hotel.viewmodel.HotelViewModel
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.JostTypography
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
                HotelCardHorizontal(
                    hotel,
                    onClick = {}
                )

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