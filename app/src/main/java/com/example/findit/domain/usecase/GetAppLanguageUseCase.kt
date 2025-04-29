package com.example.findit.domain.usecase

import javax.inject.Inject
import com.example.findit.domain.repository.LanguageRepository

class GetAppLanguageUseCase @Inject constructor(
    private val repository: LanguageRepository
)  {
    suspend operator fun invoke(): String {
        return repository.getAppLanguage()
    }
}

