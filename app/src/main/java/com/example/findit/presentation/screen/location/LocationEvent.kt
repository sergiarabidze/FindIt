package com.example.findit.presentation.screen.location

import com.google.type.LatLng

sealed class LocationEvent {
    data class OpenBottomSheet(private val postId : String) : LocationEvent()
}