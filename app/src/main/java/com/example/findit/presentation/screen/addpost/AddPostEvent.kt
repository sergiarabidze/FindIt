package com.example.findit.presentation.screen.addpost

import android.net.Uri
import com.example.findit.domain.model.PostType
import com.google.firebase.firestore.GeoPoint

sealed class AddPostEvent {
    data object OpenDialog:AddPostEvent()
    data class AddLocation(val geoPoint: GeoPoint):AddPostEvent()
    data class AddPost(val type : PostType, val description :String, val geoPoint: GeoPoint):AddPostEvent()
    data object ClearError : AddPostEvent()
    data class ImageSelected(val uri: Uri) : AddPostEvent()
}