package com.example.travelone.presentation.feature.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.travelone.presentation.components.ShimmerLoading
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.Dimens

@Composable
fun UserInfoShimmerLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.HeightLarge)
            .clip(RoundedCornerShape(AppShape.SmallShape))
    ) {
        ShimmerLoading {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray.copy(alpha = 0.2f))
            )
        }
    }
}