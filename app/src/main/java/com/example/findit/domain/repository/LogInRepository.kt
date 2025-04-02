package com.example.findit.domain.repository

import com.example.findit.domain.resource.Resource

interface LogInRepository {

    suspend fun loginUser(email: String, password: String): Resource<Boolean>

}