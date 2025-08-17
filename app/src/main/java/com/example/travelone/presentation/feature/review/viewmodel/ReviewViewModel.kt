package com.example.travelone.presentation.feature.review.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelone.domain.model.review.Review
import com.example.travelone.domain.usecase.review.GetReviewsByServiceIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor (
    private val getReviewByServiceIdUseCase: GetReviewsByServiceIdUseCase
): ViewModel() {

    private val _isReviewLoading = mutableStateOf(true)
    val isReviewLoading: State<Boolean> = _isReviewLoading

    var reviews by mutableStateOf<List<Review>>(emptyList())
        private set

    fun loadReviewsByServiceId(serviceId: String) {
        viewModelScope.launch {
            _isReviewLoading.value = true
            reviews = getReviewByServiceIdUseCase(serviceId)
            _isReviewLoading.value = false
        }
    }
}