package com.example.findit.domain.usecase

import com.example.findit.domain.model.Chat
import com.example.findit.domain.repository.GetAllChatsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChatsUseCase @Inject constructor(
    private val repository: GetAllChatsRepository
) {
    operator fun invoke(userId: String): Flow<List<Chat>> {
        return repository.getAllChatsForUser(userId)
    }
}