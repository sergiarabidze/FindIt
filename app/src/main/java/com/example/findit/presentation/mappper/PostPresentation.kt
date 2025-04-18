package com.example.findit.presentation.mappper

import com.example.findit.domain.model.LocationModel
import com.example.findit.domain.model.PostDomain
import com.example.findit.presentation.model.PostPresentation
import com.google.firebase.firestore.GeoPoint

fun PostDomain.toPresentation(): PostPresentation {
    return PostPresentation(
        imageUrl = imageUrl,
        description = description,
        userId = userId,
        timestamp = timestamp,
        location = GeoPoint(location.latitude, location.longitude)
    )
}

fun PostPresentation.toDomain(): PostDomain {
    return PostDomain(
        postId = postId,
        imageUrl = imageUrl,
        description = description,
        userId = userId,
        timestamp = timestamp,
        location = LocationModel(
            latitude = location.latitude,
            longitude = location.longitude
        )
    )
}