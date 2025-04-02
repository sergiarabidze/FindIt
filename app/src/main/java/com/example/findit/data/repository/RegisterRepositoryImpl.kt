package com.example.findit.data.repository

import com.example.findit.domain.repository.RegisterRepository
import com.example.findit.domain.resource.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : RegisterRepository {

    override suspend fun registerUser(
        email: String,
        password: String,
    ): Resource<Boolean> {
        return try {

            auth.createUserWithEmailAndPassword(email, password).await()
            Resource.Success(true)

        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unknown error occurred")
        }
    }
}