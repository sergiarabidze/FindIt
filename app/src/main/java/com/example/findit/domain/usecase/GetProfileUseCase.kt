package com.example.findit.domain.usecase

import com.example.findit.domain.model.UserProfile
import com.example.findit.domain.repository.GetUserProfileRepository
import com.example.findit.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetProfileUseCase {
    suspend operator fun invoke(): Flow<Resource<UserProfile>>
}

class GetProfileUseCaseImpl @Inject constructor(
    private val repository: GetUserProfileRepository
) : GetProfileUseCase {
    override suspend fun invoke(): Flow<Resource<UserProfile>> {
        return repository.getUserProfile()
    }
}