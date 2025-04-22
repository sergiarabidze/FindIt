package com.example.findit.domain.usecase

import com.example.findit.domain.model.PostDomain
import com.example.findit.domain.repository.PostsRepository
import com.example.findit.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repository: PostsRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<PostDomain>>> {
        return repository.getPosts()
    }

}