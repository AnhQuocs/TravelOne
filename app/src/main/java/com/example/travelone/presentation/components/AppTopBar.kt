package com.example.travelone.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.JostTypography

sealed class TopBarIcon {
    data class Vector(val imageVector: ImageVector) : TopBarIcon()
    data class Resource(val resId: Int) : TopBarIcon()
}

@Composable
fun AppTopBar(
    icon1: TopBarIcon,
    text: String,
    icon2: TopBarIcon,
    onIcon1Click: () -> Unit,
    onIcon2Click: () -> Unit,
    color: Color = Color.Black
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.HeightML),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconComponent(icon = icon1, onClick = onIcon1Click, color = color)

        Text(
            text = text,
            color = color,
            style = JostTypography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        IconComponent(icon = icon2, onClick = onIcon2Click, color = color)
    }
}

@Composable
private fun IconComponent(icon: TopBarIcon, onClick: () -> Unit, color: Color) {
    when (icon) {
        is TopBarIcon.Vector -> Icon(
            imageVector = icon.imageVector,
            contentDescription = null,
            tint = color,
            modifier = Modifier
                .size(Dimens.SizeML)
                .clickable { onClick() }
        )
        is TopBarIcon.Resource -> Icon(
            painter = painterResource(id = icon.resId),
            contentDescription = null,
            tint = color,
            modifier = Modifier
                .size(Dimens.SizeML)
                .clickable { onClick() }
        )
    }
}