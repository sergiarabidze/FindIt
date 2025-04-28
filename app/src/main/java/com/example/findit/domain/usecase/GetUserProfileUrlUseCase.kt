package com.example.findit.domain.usecase

import com.example.findit.domain.repository.UserRepository
import javax.inject.Inject

class GetUserProfileUrlUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userId: String): String {
        return repository.getUserProfile(userId)
    }
}