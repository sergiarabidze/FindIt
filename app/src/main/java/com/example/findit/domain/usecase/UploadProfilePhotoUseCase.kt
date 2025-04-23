package com.example.findit.domain.usecase

import android.graphics.Bitmap
import com.example.findit.domain.repository.UploadImageRepository
import com.example.findit.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UploadProfilePhotoUseCase @Inject constructor(
    private val repository: UploadImageRepository
) {
    suspend operator fun invoke(bitmap: Bitmap): Flow<Resource<String>> {
        return repository.uploadProfileImage(bitmap)
    }
}
