package com.example.travelone.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppLineGray(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(1.2.dp)
            .fillMaxWidth()
            .background(color = Color.LightGray.copy(alpha = 0.3f))
    )
}