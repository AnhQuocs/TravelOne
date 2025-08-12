package com.example.travelone.data.repository.search_history

import com.example.travelone.data.mapper.search_history.toDomain
import com.example.travelone.data.mapper.search_history.toDto
import com.example.travelone.data.model.search_history.SearchHistoryDto
import com.example.travelone.domain.model.search_history.SearchHistory
import com.example.travelone.domain.repository.search_history.SearchHistoryRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class SearchHistoryRepositoryImpl(
    private val firestore: FirebaseFirestore
): SearchHistoryRepository {
    override suspend fun addSearchHistory(userId: String, history: SearchHistory) {
        val collectionRef = firestore.collection("users")
            .document(userId)
            .collection("searchHistory")

        val querySnapshot = collectionRef
            .whereEqualTo("title", history.title)
            .whereEqualTo("subTitle", history.subTitle)
            .get()
            .await()

        if (querySnapshot.isEmpty) {
            val docRef = collectionRef.document(history.id)
            val dto = history.toDto()
            docRef.set(dto).await()
        } else {

        }
    }

    override suspend fun getSearchHistory(userId: String): List<SearchHistory> {
        val snapshot = firestore.collection("users")
            .document(userId)
            .collection("searchHistory")
            .orderBy("historyAt", Query.Direction.DESCENDING)
            .get()
            .await()

        return snapshot.documents.mapNotNull { it.toObject(SearchHistoryDto::class.java) ?.toDomain() }
    }

    override suspend fun clearAllSearchHistory(userId: String) {
        val batch = firestore.batch()
        val collection = firestore.collection("users")
            .document(userId)
            .collection("searchHistory")
            .get()
            .await()

        collection.documents.forEach { batch.delete(it.reference) }
        batch.commit().await()
    }
}