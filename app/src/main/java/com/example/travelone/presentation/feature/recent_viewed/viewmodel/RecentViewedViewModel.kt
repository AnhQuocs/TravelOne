package com.example.travelone.presentation.feature.recent_viewed.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelone.domain.model.recent_viewed.RecentViewed
import com.example.travelone.domain.model.recent_viewed.ViewedType
import com.example.travelone.domain.usecase.recent_viewed.AddRecentViewedUseCase
import com.example.travelone.domain.usecase.recent_viewed.GetRecentViewedUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentViewedViewModel @Inject constructor(
    private val addRecentViewedUseCase: AddRecentViewedUseCase,
    private val getRecentViewedUseCase: GetRecentViewedUseCase
) : ViewModel() {

    var recentList by mutableStateOf<List<RecentViewed>>(emptyList())
        private set

    init {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            loadRecent(userId)
        }
    }

    private fun loadRecent(userId: String) {
        viewModelScope.launch {
            recentList = getRecentViewedUseCase(userId)
        }
    }

    fun addRecent(id: String, type: ViewedType) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModelScope.launch {
            val recent = RecentViewed(
                id = id,
                type = type,
                viewedAt = System.currentTimeMillis()
            )

            addRecentViewedUseCase(userId, recent)
            loadRecent(userId)
        }
    }
}