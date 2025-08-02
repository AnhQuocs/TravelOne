package com.example.travelone.data.source

import com.example.travelone.data.model.hotel.HotelDto
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class FirebaseHotelDataSource {
    private val collection = Firebase.firestore.collection("hotels")

    suspend fun fetchAllHotels(): List<Pair<String, HotelDto>> {
        return collection.get().await().map { doc ->
            doc.id to doc.toObject(HotelDto::class.java)
        }
    }
}