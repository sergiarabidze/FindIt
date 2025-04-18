package com.example.findit.data.repository

import com.example.findit.domain.model.UserProfile
import com.example.findit.domain.repository.GetUserProfileRepository
import com.example.findit.domain.resource.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetUserProfileRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : GetUserProfileRepository {

    override suspend fun getUserProfile(): Flow<Resource<UserProfile>> = flow {
        emit(Resource.Loader(true))
        try {
            val uid = auth.currentUser?.uid ?: throw Exception("User not logged in")
            val snapshot = firestore.collection("users").document(uid).get().await()
            val userProfile = UserProfile(
                name = snapshot.getString("name") ?: "",
                surname = snapshot.getString("surname") ?: "",
                phone = snapshot.getString("phone") ?: "",
                email = snapshot.getString("email") ?: "",
                password = snapshot.getString("password") ?: ""
            )
            emit(Resource.Success(userProfile))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Load failed"))
        }
    }
}