package com.example.travelone.domain.repository.location

import com.google.android.gms.maps.model.LatLng

interface LocationRepository {
    suspend fun getCurrentLocation(): LatLng?
}