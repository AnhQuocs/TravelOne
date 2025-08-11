package com.example.travelone.data.repository.recent_viewed

import com.example.travelone.data.mapper.recent_viewed.toDomain
import com.example.travelone.data.mapper.recent_viewed.toDto
import com.example.travelone.data.model.recent_viewed.RecentViewedDto
import com.example.travelone.domain.model.recent_viewed.RecentViewed
import com.example.travelone.domain.repository.recent_viewed.RecentViewedRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class RecentViewedRepositoryImpl (
    private val firestore: FirebaseFirestore
): RecentViewedRepository {

    override suspend fun addRecentViewed(userId: String, recent: RecentViewed) {
        val docRef = firestore.collection("users")
            .document(userId)
            .collection("recentViewed")
            .document(recent.id)

        val dto = recent.toDto()
        docRef.set(dto)
    }

    override suspend fun getRecentViewed(userId: String): List<RecentViewed> {
        val snapshot = firestore.collection("users")
            .document(userId)
            .collection("recentViewed")
            .orderBy("viewedAt", Query.Direction.DESCENDING)
            .get()
            .await()

        return snapshot.documents.mapNotNull { it.toObject(RecentViewedDto::class.java)?.toDomain() }
    }

    override suspend fun clearRecentViewed(userId: String) {
        val batch = firestore.batch()
        val collection = firestore.collection("users")
            .document(userId)
            .collection("recentViewed")
            .get()
            .await()

        collection.documents.forEach { batch.delete(it.reference) }
        batch.commit().await()
    }
}