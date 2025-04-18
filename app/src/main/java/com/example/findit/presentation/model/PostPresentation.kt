package com.example.findit.presentation.model

import com.google.firebase.firestore.GeoPoint
import java.util.UUID

data class PostPresentation(
    val postId: String = UUID.randomUUID().toString(),
    val imageUrl: String = "",
    val description: String = "",
    val userId: String = "",
    val timestamp: Long = 0,
    val location: GeoPoint = GeoPoint(0.0, 0.0)
)