package com.example.findit.data.dto

import com.google.firebase.firestore.GeoPoint

data class PostDto(
    val postId: String,
    val imageUrl: String,
    val description: String,
    val userId: String,
    val timestamp: Long,
    val location: GeoPoint
)