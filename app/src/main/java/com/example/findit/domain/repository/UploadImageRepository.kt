package com.example.findit.domain.repository

import android.graphics.Bitmap
import com.example.findit.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

interface UploadImageRepository {
    suspend fun uploadImage(bitmap:Bitmap): Flow<Resource<String>>
}