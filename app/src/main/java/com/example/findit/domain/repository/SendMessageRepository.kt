package com.example.findit.domain.repository

import com.example.findit.domain.model.ChatMessage

interface SendMessageRepository {
    suspend fun sendMessage(chatId: String, message: ChatMessage)
}