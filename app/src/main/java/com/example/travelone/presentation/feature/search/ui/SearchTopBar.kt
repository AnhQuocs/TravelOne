package com.example.travelone.presentation.feature.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.travelone.R
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.JostTypography

@Composable
fun SearchTopBar(
    onBackClick: () -> Unit,
    onNotification: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.HeightML),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Black),
            modifier = Modifier
                .size(Dimens.SizeML)
                .clickable { onBackClick() }
        )

        Text(
            text = stringResource(id = R.string.search),
            style = JostTypography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_notification),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Black),
            modifier = Modifier
                .size(Dimens.SizeML)
                .clickable { onNotification() }
        )
    }
}