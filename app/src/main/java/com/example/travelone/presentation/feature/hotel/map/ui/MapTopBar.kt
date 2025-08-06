package com.example.travelone.presentation.feature.hotel.map.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.travelone.R
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.JostTypography

@Composable
fun MapTopBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(horizontal = Dimens.PaddingM)
            .fillMaxWidth()
            .height(Dimens.HeightDefault)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .background(color = Color.White, CircleShape)
                .size(Dimens.SizeXLPlus)
                .align(Alignment.CenterStart)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Black),
                modifier = Modifier.size(Dimens.SizeM)
            )
        }

        Text(
            text = stringResource(id = R.string.map_hotel_near),
            style = JostTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.Black
        )

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .size(Dimens.SizeXLPlus)
                .align(Alignment.CenterEnd)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Black),
                modifier = Modifier.size(Dimens.SizeML)
            )
        }
    }
}