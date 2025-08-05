package com.example.travelone.domain.usecase.location

import com.example.travelone.domain.repository.location.LocationRepository
import com.google.android.gms.maps.model.LatLng

class GetUserLocationUseCase (
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): LatLng? = locationRepository.getCurrentLocation()
}