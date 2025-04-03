package com.example.findit.domain.repository

import com.example.findit.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository  {
    suspend fun registerUser(email: String, password: String): Flow<Resource<Boolean>>
}
