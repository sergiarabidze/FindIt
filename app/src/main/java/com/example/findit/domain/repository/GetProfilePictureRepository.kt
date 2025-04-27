package com.example.findit.domain.repository

import com.example.findit.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

interface GetProfilePictureRepository {
    suspend fun getProfilePictureUrl(userid :String): Flow<Resource<String>>
}
