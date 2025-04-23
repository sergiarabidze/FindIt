package com.example.findit.domain.repository

import com.example.findit.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

interface UpdateProfileImageRepository {
    suspend fun updateProfileImageUrl(imageUrl: String): Flow<Resource<Unit>>
}
