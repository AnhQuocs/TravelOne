package com.example.travelone.data.source

import com.example.travelone.data.model.flight.FlightDto
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class FirebaseFlightDataSource {
    private val collection = Firebase.firestore.collection("flights")

    suspend fun fetchAllFlights(): List<Pair<String, FlightDto>> {
        return collection.get().await().map { doc ->
            doc.id to doc.toObject(FlightDto::class.java)
        }
    }

    suspend fun fetchFlightById(flightId: String): FlightDto? {
        val doc = collection
            .document(flightId)
            .get()
            .await()

        return doc.toObject(FlightDto::class.java)
    }
}