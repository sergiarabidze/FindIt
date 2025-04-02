package com.example.findit.data.repository

import com.example.findit.domain.repository.LogInRepository
import com.example.findit.domain.resource.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LogInRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : LogInRepository {
    override suspend fun loginUser(email: String, password: String): Resource<Boolean> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Login failed")
        }
    }
}