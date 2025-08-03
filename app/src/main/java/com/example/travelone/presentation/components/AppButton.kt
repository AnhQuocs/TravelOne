package com.example.travelone.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.travelone.ui.theme.Dimens

@Composable
fun AppButton(
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    shape: androidx.compose.ui.graphics.Shape,
    modifier: Modifier = Modifier,
//    content: @Composable RowScope.() -> Unit
    text: String
) {
    Button(
        onClick = onClick,
        colors = colors,
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.HeightDefault),
        shape = shape
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
    }
}