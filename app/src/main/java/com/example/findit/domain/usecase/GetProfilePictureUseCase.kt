package com.example.findit.domain.usecase

import com.example.findit.domain.repository.GetProfilePictureRepository
import com.example.findit.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfilePictureUseCase @Inject constructor(
    private val repository: GetProfilePictureRepository
) {
    suspend operator fun invoke(userid :String): Flow<Resource<String>> {
        return repository.getProfilePictureUrl(userid = userid)
    }
}
