package com.example.travelone.presentation.feature.weather.ui


import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travelone.R
import com.example.travelone.constant.Const.permissions
import com.example.travelone.presentation.feature.weather.viewmodel.STATE
import com.example.travelone.presentation.feature.weather.viewmodel.WeatherViewModel
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.CyanBright
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.MintLight
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

@Composable
fun WeatherSection(viewModel: WeatherViewModel = hiltViewModel(), context: Context) {
    val fusedClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    var locationGranted by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        locationGranted = it.values.all { granted -> granted }
    }

    LaunchedEffect(Unit) {
        if (permissions.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }) {
            locationGranted = true
        } else {
            launcher.launch(permissions)
        }
    }

    LaunchedEffect(locationGranted) {
        if (locationGranted) {
            fusedClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { location ->
                    location?.let {
                        viewModel.getWeather(it.latitude, it.longitude)
                    }
                }
        }
    }

    when (viewModel.state) {
        STATE.LOADING -> WeatherLoading()
        STATE.FAILED -> ErrorCard()
        STATE.SUCCESS -> viewModel.weather?.let {
            WeatherCard(it)
        }
    }
}

@Composable
fun ErrorCard() {
    val gradient = Brush.linearGradient(
        colors = listOf(MintLight, CyanBright),
        start = Offset(1000f, -1000f),
        end = Offset(1000f, 1000f)
    )

    Box(
        modifier = Modifier
            .padding(vertical = Dimens.PaddingM, horizontal = Dimens.PaddingS)
            .padding(top = Dimens.PaddingM)
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
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CloudOff,
                        contentDescription = null,
                        modifier = Modifier.size(Dimens.SizeXXL),
                        tint = Color.White
                    )

                    Spacer(modifier = Modifier.width(AppSpacing.Large))

                    Text(
                        text = stringResource(id = R.string.error),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                }
            }
        }
    }
}