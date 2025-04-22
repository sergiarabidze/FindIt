package com.example.findit.domain.usecase

import com.example.findit.domain.repository.PostsRepository
import javax.inject.Inject

class SearchPostsUseCase @Inject constructor (
    val repository: PostsRepository
){
    suspend operator fun invoke(query: String) = repository.searchPosts(query)
}