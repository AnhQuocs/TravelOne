package com.example.travelone.presentation.feature.hotel.map.ui

import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.travelone.R
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.JostTypography
import com.example.travelone.ui.theme.PrimaryBlue
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@Composable
fun MiniMap(
    userLocation: LatLng?,
    onOpenMapClicked: (LatLng) -> Unit,
) {
    if(userLocation == null) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.hotel_nearby),
                    color = Color.Black,
                    style = JostTypography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )

                Text(
                    text = stringResource(id = R.string.open_map),
                    style = JostTypography.bodyMedium.copy(lineHeight = 0.sp),
                    color = PrimaryBlue,
                    modifier = Modifier.clickable {
                        onOpenMapClicked(userLocation)
                    }
                )
            }

            Spacer(modifier = Modifier.height(AppSpacing.MediumPlus))

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
            ) {
                MiniMapView(latLng = userLocation)
            }
        }
    }
}

@Composable
fun MiniMapView(latLng: LatLng, modifier: Modifier = Modifier) {
    val mapView = rememberMapViewWithLifecycle()

    AndroidView(
        factory = { mapView },
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        mapView.getMapAsync { googleMap ->
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            googleMap.addMarker(MarkerOptions().position(latLng).title("You are here"))

            googleMap.uiSettings.apply {
                isZoomControlsEnabled = false
                isScrollGesturesEnabled = false
                isZoomGesturesEnabled = false
                isMapToolbarEnabled = false
            }
        }
    }
}

@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply { onCreate(Bundle()) }
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        val observer = object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) = mapView.onStart()
            override fun onResume(owner: LifecycleOwner) = mapView.onResume()
            override fun onPause(owner: LifecycleOwner) = mapView.onPause()
            override fun onStop(owner: LifecycleOwner) = mapView.onStop()
            override fun onDestroy(owner: LifecycleOwner) = mapView.onDestroy()
        }

        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer) }
    }

    return mapView
}