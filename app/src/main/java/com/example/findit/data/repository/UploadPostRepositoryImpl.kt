package com.example.findit.data.repository

import com.example.findit.data.mapper.toFirestoreMap
import com.example.findit.data.request.ApiHelper
import com.example.findit.data.util.FirestoreKeys
import com.example.findit.domain.model.PostDomain
import com.example.findit.domain.repository.UploadPostRepository
import com.example.findit.domain.resource.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UploadPostRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val apiHelper: ApiHelper
) : UploadPostRepository {

    override suspend fun uploadPost(post: PostDomain): Flow<Resource<Boolean>> {
        return apiHelper.safeFireBaseCall {
            val postData =post.toFirestoreMap()
            firestore.collection(FirestoreKeys.POSTS)
                .document(post.postId)
                .set(postData)
        }.map { resource ->
            when (resource) {
                is Resource.Success -> Resource.Success(true)
                is Resource.Error -> Resource.Error(resource.errorMessage)
                is Resource.Loader -> Resource.Loader(resource.isLoading)
            }
        }
    }

}
