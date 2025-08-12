package com.example.travelone.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.travelone.ui.theme.JostTypography
import com.example.travelone.ui.theme.PrimaryBlue

@Composable
fun TitleSection(text1: String, text2: String, onClick: () -> Unit = {}, color: Color = PrimaryBlue) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text1,
            color = Color.Black,
            style = JostTypography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )

        Text(
            text = text2,
            style = JostTypography.bodyMedium.copy(lineHeight = 0.sp),
            color = color,
            modifier = Modifier.clickable { onClick() }
        )
    }
}