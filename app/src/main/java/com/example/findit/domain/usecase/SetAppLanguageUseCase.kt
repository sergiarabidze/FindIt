package com.example.findit.domain.usecase

import com.example.findit.domain.repository.LanguageRepository
import javax.inject.Inject

interface SetAppLanguageUseCase {
    suspend operator fun invoke(language: String)
}
class SetAppLanguageUseCaseImpl @Inject constructor(
    private val repository: LanguageRepository
) : SetAppLanguageUseCase {
    override suspend fun invoke(language: String) {
        repository.setAppLanguage(language)
    }
}
