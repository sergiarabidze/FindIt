package com.example.findit.data.repository

import android.graphics.Bitmap
import com.example.findit.domain.repository.UploadImageRepository
import com.example.findit.domain.resource.Resource
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.UUID
import javax.inject.Inject

class UploadImageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage
) : UploadImageRepository {
    override suspend fun uploadImage(bitmap: Bitmap): Flow<Resource<String>> = flow {
        try {
            val baos = ByteArrayOutputStream().apply {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, this)
            }

            val data = baos.toByteArray()
            val fileName = "${UUID.randomUUID()}.jpg"
            val photoRef = storage.reference.child("post_images/$fileName")
            photoRef.putBytes(data).await()
            val downloadUrl = photoRef.downloadUrl.await().toString()
            emit(Resource.Success(downloadUrl))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error during upload"))
        }
    }.flowOn(Dispatchers.IO)
}