package com.example.findit.data.mapper

import com.example.findit.data.dto.PostDto
import com.example.findit.data.util.FirestoreKeys
import com.example.findit.domain.model.LocationModel
import com.example.findit.domain.model.PostDomain
import com.google.firebase.firestore.GeoPoint

fun PostDto.toDomain(): PostDomain {
    return PostDomain(
        postId = postId,
        imageUrl = imageUrl,
        description = description,
        userId = userId,
        timestamp = timestamp,
        location = location.toDomain(),
        postType = postType
    )
}

fun GeoPoint.toDomain(): LocationModel {
    return LocationModel(
        latitude = latitude,
        longitude = longitude
    )
}

fun PostDomain.toDto(): PostDto {
    return PostDto(
        postId = postId,
        imageUrl = imageUrl,
        description = description,
        userId = userId,
        timestamp = timestamp,
        location = GeoPoint(location.latitude, location.longitude),
        postType = postType
    )
}
fun PostDomain.toFirestoreMap(): Map<String, Any> {
    return mapOf(
        FirestoreKeys.POST_ID to postId,
        FirestoreKeys.IMAGE_URL to imageUrl,
        FirestoreKeys.DESCRIPTION to description,
        FirestoreKeys.USER_ID to userId,
        FirestoreKeys.TIMESTAMP to timestamp,
        FirestoreKeys.LOCATION to mapOf(
            FirestoreKeys.LATITUDE to location.latitude,
            FirestoreKeys.LONGITUDE to location.longitude
        ),
        FirestoreKeys.POST_TYPE to postType.toString()
    )
}