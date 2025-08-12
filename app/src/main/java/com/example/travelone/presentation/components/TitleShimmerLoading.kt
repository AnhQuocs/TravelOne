package com.example.travelone.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.Dimens

@Composable
fun TitleShimmerLoading() {
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