package com.example.findit.presentation.screen.addpost

import android.net.Uri
import com.google.firebase.firestore.GeoPoint

sealed class AddPostEvent {
    data object OpenDialog:AddPostEvent()
    data class AddLocation(val geoPoint: GeoPoint):AddPostEvent()
    data class AddPost(val description :String, val geoPoint: GeoPoint):AddPostEvent()
    data object ClearError : AddPostEvent()
    data class ImageSelected(val uri: Uri) : AddPostEvent()
}