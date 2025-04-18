package com.example.findit.domain.usecase


import com.example.findit.domain.model.UserProfile
import com.example.findit.domain.repository.UpdateUserProfileRepository
import com.example.findit.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UpdateProfileUseCase {
    suspend operator fun invoke(profile: UserProfile): Flow<Resource<Boolean>>
}

class UpdateProfileUseCaseImpl @Inject constructor(
    private val repository: UpdateUserProfileRepository
) : UpdateProfileUseCase {
    override suspend fun invoke(profile: UserProfile): Flow<Resource<Boolean>> {
        return repository.updateUserProfile(profile)
    }
}
