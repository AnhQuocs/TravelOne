package com.example.travelone.presentation.feature.hotel.map.ui

import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.travelone.R
import com.example.travelone.presentation.components.TitleSection
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.AppSpacing
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@Composable
fun MiniMap(
    location: LatLng?,
    onOpenMapClicked: (LatLng) -> Unit,
    mapView: MapView,
    markerIcon: BitmapDescriptor? = null
) {
    if (location == null) {
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
            TitleSection(
                text1 = stringResource(id = R.string.hotel_nearby),
                text2 = stringResource(id = R.string.open_map),
                onClick = { onOpenMapClicked(location) }
            )

            Spacer(modifier = Modifier.height(AppSpacing.MediumPlus))

            MiniMapView(
                latLng = location,
                mapView = mapView,
                markerIcon = markerIcon,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(AppShape.ExtraLargeShape))
            )
        }
    }
}

@Composable
fun MiniMapView(
    latLng: LatLng,
    mapView: MapView,
    markerIcon: BitmapDescriptor? = null,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { mapView },
        modifier = modifier
    ) {
        mapView.getMapAsync { googleMap ->
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            googleMap.clear()

            val markerOptions = MarkerOptions()
                .position(latLng)
                .title(if (markerIcon == null) "You are here" else "Hotel here")

            markerIcon?.let { markerOptions.icon(it) }

            googleMap.addMarker(markerOptions)

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