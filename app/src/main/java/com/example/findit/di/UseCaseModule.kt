package com.example.findit.di

import com.example.findit.domain.usecase.RegisterValidationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideValidateRegisterUseCase() : RegisterValidationUseCase {
        return RegisterValidationUseCase()
    }

}