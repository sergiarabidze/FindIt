package com.example.findit.domain.usecase

import com.example.findit.domain.repository.PostsRepository
import javax.inject.Inject

class GetPostByIdUseCase @Inject constructor(
    private val repository : PostsRepository
){
    suspend operator fun invoke(postId : String) = repository.getPostById(postId)
}