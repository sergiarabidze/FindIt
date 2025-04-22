package com.example.findit.domain.repository

interface UserRepository {
    fun getCurrentUserId(): String?
    suspend fun getUserFullName(userId: String): String
}