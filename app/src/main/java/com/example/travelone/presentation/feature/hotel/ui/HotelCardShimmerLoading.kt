package com.example.travelone.presentation.feature.hotel.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.travelone.presentation.components.ShimmerLoading
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.Dimens

@Composable
fun HotelCardShimmerLoading() {
    ShimmerLoading(
        content = {
            Column(
                modifier = Modifier
                    .padding(Dimens.PaddingS)
                    .padding(bottom = Dimens.PaddingS)
                    .align(Alignment.BottomCenter)
            ) {
                Box(
                    modifier = Modifier
                        .padding(end = Dimens.PaddingXSPlus)
                        .height(Dimens.HeightText)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(AppShape.SmallShape))
                        .background(Color.Gray.copy(alpha = 0.2f))
                )

                Spacer(modifier = Modifier.height(AppSpacing.MediumLarge))

                Box(
                    modifier = Modifier
                        .padding(end = Dimens.PaddingXXL)
                        .height(Dimens.HeightSmall)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(AppShape.SmallShape))
                        .background(Color.Gray.copy(alpha = 0.2f))
                )

                Spacer(modifier = Modifier.height(AppSpacing.MediumLarge))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .height(Dimens.HeightText - 3.dp)
                            .fillMaxWidth(0.6f)
                            .clip(RoundedCornerShape(AppShape.SmallShape))
                            .background(Color.Gray.copy(alpha = 0.2f))
                    )

                    Box(
                        modifier = Modifier
                            .height(Dimens.HeightText - 3.dp)
                            .fillMaxWidth(0.25f)
                            .clip(RoundedCornerShape(AppShape.SmallShape))
                            .background(Color.Gray.copy(alpha = 0.2f))
                    )
                }
            }
        }
    )
}

@Composable
fun MostPopularShimmerLoading() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(Dimens.HeightXS)
                .clip(RoundedCornerShape(AppShape.SmallShape))
        ) {
            ShimmerLoading {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(Color.Gray.copy(alpha = 0.2f))
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .height(Dimens.HeightXS)
                .clip(RoundedCornerShape(AppShape.SmallShape))
        ) {
            ShimmerLoading {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(Color.Gray.copy(alpha = 0.2f))
                )
            }
        }
    }
}