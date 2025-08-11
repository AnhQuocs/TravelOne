package com.example.travelone.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.travelone.R

@Composable
fun formatDuration(durationMinutes: Int): String {
    val hours = durationMinutes / 60
    val minutes = durationMinutes % 60

    return buildString {
        if (hours > 0) {
            append(
                if (hours == 1) "1${stringResource(id = R.string.hour)}"
                else "$hours${stringResource(id = R.string.hour)}"
            )
        }
        if (minutes > 0) {
            if (hours > 0) append(" ")
            append("$minutes${stringResource(id = R.string.minutes)}")
        }
        if (hours == 0 && minutes == 0) {
            append("0${stringResource(id = R.string.minutes)}")
        }
    }
}