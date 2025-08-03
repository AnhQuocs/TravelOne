package com.example.travelone.presentation.feature.weather.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.travelone.domain.model.weather.WeatherResult
import com.example.travelone.presentation.feature.weather.util.BuildIcon.buildIcon
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.CyanBright
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.MintLight

@Composable
fun WeatherCard(weather: WeatherResult) {
    val gradient = Brush.linearGradient(
        colors = listOf(MintLight, CyanBright),
        start = Offset(1000f, -1000f),
        end = Offset(1000f, 1000f)
    )

    val textShadow = Shadow(
        color = Color.Black.copy(alpha = 0.3f),
        offset = Offset(2f, 2f),
        blurRadius = 4f
    )

    Box(
        modifier = Modifier
            .padding(bottom = Dimens.PaddingM)
            .fillMaxWidth()
            .height(Dimens.HeightXL2)
            .background(brush = gradient, shape = RoundedCornerShape(AppShape.MediumShape))
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            shape = RoundedCornerShape(AppShape.MediumShape),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = Dimens.PaddingM)
                        .align(Alignment.TopStart),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = buildIcon(weather.weather?.firstOrNull()?.icon ?: ""),
                        contentDescription = null,
                        modifier = Modifier
                            .size(Dimens.SizeUltra)
                            .align(Alignment.CenterStart),
                        onSuccess = {
                            Log.d("WeatherImage", "Image loaded successfully.")
                        },
                        onError = {
                            Log.e("WeatherImage", "Image failed to load: ${it.result.throwable}")
                        }
                    )

                    Log.d("WeatherImage", weather.weather?.firstOrNull()?.icon ?: "don't have")

                    Text(
                        text = weather.weather?.firstOrNull()?.description
                            ?.replaceFirstChar { it.titlecase() }
                            ?: "",
                        style = MaterialTheme.typography.bodyLarge.merge(
                            TextStyle(shadow = textShadow)
                        ),
                        color = Color.White,
                        modifier = Modifier.align(Alignment.BottomStart)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(Dimens.PaddingM)
                        .align(Alignment.TopEnd),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "${weather.main?.temp ?: "--"}°C",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp).merge(
                            TextStyle(shadow = textShadow)
                        ),
                        color = Color.White,
                    )

                    Spacer(modifier = Modifier.height(AppSpacing.LargePlus))

                    Text(
                        text = weather.name ?: "",
                        style = MaterialTheme.typography.titleSmall.merge(
                            TextStyle(shadow = textShadow)
                        ),
                        color = Color.White
                    )
                }
            }
        }
    }
}