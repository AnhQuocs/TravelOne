package com.example.travelone.domain.model.review

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val userId: String,
    val userName: String,
    val userProfilePicture: String,
    val serviceId: String,
    val serviceType: String,
    val rating: Int,
    val comment: String,
    val timestamp: String
): Parcelable