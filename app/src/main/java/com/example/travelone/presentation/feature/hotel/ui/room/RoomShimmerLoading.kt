package com.example.travelone.presentation.feature.hotel.ui.room

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
fun RoomShimmerLoading() {
    val alpha = 0.3f
    val item = 3

    ShimmerLoading(
        modifier = Modifier
            .height(Dimens.HeightXL4 - 20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
               modifier = Modifier
                   .height(Dimens.HeightXS)
                   .fillMaxWidth(0.25f)
                   .clip(RoundedCornerShape(AppShape.SmallShape))
                   .background(color = Color.Black.copy(alpha))
            )

            Spacer(modifier = Modifier.height(AppSpacing.Medium))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.HeightXL4 - 20.dp),
                horizontalArrangement = Arrangement.spacedBy(Dimens.PaddingS)
            ) {
                Box(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(AppShape.LargeShape))
                        .background(color = Color.Black.copy(alpha))
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(Dimens.PaddingS)
                ) {
                    for (i in 0 until item) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(AppShape.LargeShape))
                                .background(color = Color.Black.copy(alpha))
                        )
                    }
                }
            }
        }
    }
}