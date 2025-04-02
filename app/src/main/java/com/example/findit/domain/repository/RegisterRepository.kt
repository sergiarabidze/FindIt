package com.example.findit.domain.repository

import com.example.findit.domain.resource.Resource

interface RegisterRepository  {
    suspend fun registerUser(email: String, password: String): Resource<Boolean>
}
