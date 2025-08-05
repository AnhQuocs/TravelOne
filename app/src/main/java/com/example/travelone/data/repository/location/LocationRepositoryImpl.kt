package com.example.travelone.data.repository.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.example.travelone.domain.repository.location.LocationRepository
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class LocationRepositoryImpl(
    private val context: Context
): LocationRepository {
    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): LatLng? {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        return suspendCancellableCoroutine { cont ->
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if(location != null) {
                        cont.resume(LatLng(location.latitude, location.longitude))
                    } else {
                        cont.resume(null)
                    }
                }
                .addOnFailureListener {
                    cont.resume(null)
                }
        }
    }
}