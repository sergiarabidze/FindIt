package com.example.findit.domain.model

import java.util.UUID

data class PostDomain(
    val postId: String = UUID.randomUUID().toString(),
    val imageUrl: String,
    val description: String,
    val userId: String,
    val timestamp: Long,
    val location: LocationModel
)