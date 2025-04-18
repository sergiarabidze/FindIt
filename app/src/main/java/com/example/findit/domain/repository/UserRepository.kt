package com.example.findit.domain.repository

interface UserRepository {
    fun getCurrentUserId(): String?
}