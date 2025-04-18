package com.example.findit.presentation.screen.addpost

import android.graphics.Bitmap

data class AddPostState(
    val error : String? = null,
    val isLoading : Boolean = false,
    val btnEnabled : Boolean = false,
    val bitmap: Bitmap? = null
)