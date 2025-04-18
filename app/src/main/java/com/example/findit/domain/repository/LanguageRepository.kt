package com.example.findit.domain.repository

interface LanguageRepository {
    suspend fun setAppLanguage(language: String)
    suspend fun getAppLanguage(): String
}
