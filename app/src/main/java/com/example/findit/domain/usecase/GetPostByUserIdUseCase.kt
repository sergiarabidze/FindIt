package com.example.findit.domain.usecase

import com.example.findit.domain.repository.PostsRepository
import javax.inject.Inject

class GetPostByUserIdUseCase @Inject constructor(
    private val repository: PostsRepository
) {
    suspend operator fun invoke(userId: String) = repository.getPostsByUserID(userId)
}