package com.example.travelone.data.source

import com.example.travelone.data.model.room.RoomDto
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class FirebaseRoomDataSource {
    private val collection = Firebase.firestore.collection("rooms")

    suspend fun fetchRoomsByHotelId(hotelId: String): List<Pair<String, RoomDto>> {
        return collection
            .whereEqualTo("hotelId", hotelId)
            .get()
            .await()
            .documents
            .mapNotNull { doc ->
                val dto = doc.toObject(RoomDto::class.java)
                dto?.let { doc.id to it }
            }
    }

    suspend fun fetchRoomById(id: String): RoomDto? {
        val doc = collection.document(id).get().await()
        return if (doc.exists()) doc.toObject(RoomDto::class.java) else null
    }
}