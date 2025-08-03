package com.example.travelone.presentation.feature.hotel.shimmer_loading

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.SoftGray

@Preview(showBackground = true)
@Composable
fun HotelCardShimmerLoading() {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "Shimmer")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500),
            repeatMode = RepeatMode.Restart
        ),
        label = "Shimmer"
    )

    Box(
        modifier = Modifier
            .height(Dimens.HeightShimmer)
            .fillMaxWidth()
            .onGloballyPositioned { size = it.size }
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            SoftGray,
                            Color.White,
                            SoftGray
                        ),
                        start = Offset(startOffsetX, 0f),
                        end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
                    )
                )
        )

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
                        .padding(end = Dimens.PaddingXSPlus)
                        .height(Dimens.HeightText - 3.dp)
                        .fillMaxWidth(0.6f)
                        .clip(RoundedCornerShape(AppShape.SmallShape))
                        .background(Color.Gray.copy(alpha = 0.2f))
                )

                Box(
                    modifier = Modifier
                        .padding(end = Dimens.PaddingXSPlus)
                        .height(Dimens.HeightText - 3.dp)
                        .fillMaxWidth(0.25f)
                        .clip(RoundedCornerShape(AppShape.SmallShape))
                        .background(Color.Gray.copy(alpha = 0.2f))
                )
            }
        }
    }
}