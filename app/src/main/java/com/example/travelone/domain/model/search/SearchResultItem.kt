package com.example.travelone.domain.model.search

import com.example.travelone.domain.model.flight.Flight
import com.example.travelone.domain.model.hotel.Hotel

sealed class SearchResultItem {
    data class HotelItem(val hotel: Hotel) : SearchResultItem()
    data class FlightItem(val flight: Flight) : SearchResultItem()
    // Sau này thêm:
    // data class TrainItem(val train: Train) : SearchResultItem()
}