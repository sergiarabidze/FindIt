package com.example.findit.data.repository

import com.example.findit.domain.model.UserProfile
import com.example.findit.domain.repository.UpdateUserProfileRepository
import com.example.findit.domain.resource.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UpdateUserProfileRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : UpdateUserProfileRepository {

    override suspend fun updateUserProfile(profile: UserProfile): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loader(true))
        try {
            val uid = auth.currentUser?.uid ?: throw Exception("User not logged in")
            val profileMap = hashMapOf(
                "name" to profile.name,
                "surname" to profile.surname,
                "phone" to profile.phone,
                "email" to profile.email,
                "password" to profile.password
            )
            firestore.collection("users").document(uid).update(profileMap as Map<String, Any>).await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Update failed"))
        }
    }
}