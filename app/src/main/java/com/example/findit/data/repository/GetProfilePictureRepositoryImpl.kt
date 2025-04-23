package com.example.findit.data.repository

import com.example.findit.data.request.ApiHelper
import com.example.findit.data.util.FirestoreKeys
import com.example.findit.domain.repository.GetProfilePictureRepository
import com.example.findit.domain.resource.Resource
import com.example.findit.domain.resource.mapResourceData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import android.util.Log

class GetProfilePictureRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val apiHelper: ApiHelper
) : GetProfilePictureRepository {

    override suspend fun getProfilePictureUrl(): Flow<Resource<String>> {
        val uid = auth.currentUser?.uid ?: return flow {
            emit(Resource.Error("User not logged in"))
        }
        return apiHelper.safeFireBaseCall {
            firestore.collection(FirestoreKeys.USERS).document(uid).get()
        }.mapResourceData { snapshot ->
            val imageUrl = snapshot.getString(FirestoreKeys.PROFILE_IMAGE)
            Log.d("ProfileImageRepo", "📸 Image URL from Firestore: $imageUrl")
            imageUrl ?: ""
        }
    }
}
