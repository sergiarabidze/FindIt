package com.example.findit.domain.usecase

import com.example.findit.domain.model.ChatMessage
import com.example.findit.domain.repository.GetMessagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val repository: GetMessagesRepository
) {
    operator fun invoke(chatId: String): Flow<List<ChatMessage>> {
        return repository.getMessages(chatId)
    }
}