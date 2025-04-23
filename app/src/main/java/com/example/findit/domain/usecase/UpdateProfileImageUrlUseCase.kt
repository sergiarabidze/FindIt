package com.example.findit.domain.usecase

import com.example.findit.domain.repository.UpdateProfileImageRepository
import com.example.findit.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateProfileImageUrlUseCase @Inject constructor(
    private val repository: UpdateProfileImageRepository
) {
    suspend operator fun invoke(imageUrl: String): Flow<Resource<Unit>> {
        return repository.updateProfileImageUrl(imageUrl)
    }
}
