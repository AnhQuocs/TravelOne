package com.example.travelone.domain.model.search

import android.os.Parcelable
import com.example.travelone.domain.model.flight.Flight
import com.example.travelone.domain.model.hotel.Hotel
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class SearchResultItem : Parcelable {
    @Parcelize
    data class HotelItem(val hotel: Hotel) : SearchResultItem()

    @Parcelize
    data class FlightItem(val flight: Flight) : SearchResultItem()
    // Sau này thêm:
    // data class TrainItem(val train: Train) : SearchResultItem()
}