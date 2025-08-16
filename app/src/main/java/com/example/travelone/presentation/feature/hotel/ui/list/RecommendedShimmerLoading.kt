package com.example.travelone.presentation.feature.hotel.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.travelone.presentation.components.ShimmerLoading
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.Dimens

@Composable
fun RecommendedCardShimmerLoading() {
    ShimmerLoading {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .background(Color.Gray.copy(alpha = 0.2f))
            )

            Spacer(modifier = Modifier.width(AppSpacing.Medium))

            Column(
                modifier = Modifier.fillMaxHeight().padding(vertical = Dimens.PaddingS),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(Dimens.HeightXXS)
                        .background(Color.Gray.copy(alpha = 0.2f))
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(Dimens.HeightXXS)
                        .background(Color.Gray.copy(alpha = 0.2f))
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.35f)
                        .height(Dimens.HeightXXS)
                        .background(Color.Gray.copy(alpha = 0.2f))
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .padding(end = Dimens.PaddingS)
                    .fillMaxWidth(0.25f)
                    .height(Dimens.HeightXS)
                    .background(Color.Gray.copy(alpha = 0.2f))
            )
        }
    }
}

@Composable
fun RecommendedTextShimmerLoading() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
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