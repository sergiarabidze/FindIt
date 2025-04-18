package com.example.findit.domain.usecase

import javax.inject.Inject
import com.example.findit.domain.repository.LanguageRepository

interface GetAppLanguageUseCase {
    suspend operator fun invoke(): String
}

class GetAppLanguageUseCaseImpl @Inject constructor(
    private val repository: LanguageRepository
) : GetAppLanguageUseCase {
    override suspend fun invoke(): String {
        return repository.getAppLanguage()
    }
}

