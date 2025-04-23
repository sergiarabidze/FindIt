package com.example.findit.di

import com.example.findit.domain.usecase.GetAppLanguageUseCase
import com.example.findit.domain.usecase.GetAppLanguageUseCaseImpl
import com.example.findit.domain.usecase.GetProfileUseCase
import com.example.findit.domain.usecase.GetProfileUseCaseImpl
import com.example.findit.domain.usecase.RegisterValidationUseCase
import com.example.findit.domain.usecase.SetAppLanguageUseCase
import com.example.findit.domain.usecase.SetAppLanguageUseCaseImpl
import com.example.findit.domain.usecase.UpdateProfileUseCase
import com.example.findit.domain.usecase.UpdateProfileUseCaseImpl
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


    @Provides
    fun provideSetAppLanguageUseCase(
        impl: SetAppLanguageUseCaseImpl
    ): SetAppLanguageUseCase = impl

    @Provides
    fun provideGetAppLanguageUseCase(
        impl: GetAppLanguageUseCaseImpl
    ): GetAppLanguageUseCase = impl

    @Provides
    fun provideGetProfileUseCase(
        impl : GetProfileUseCaseImpl
    ): GetProfileUseCase = impl

    @Provides
    fun provideUpdateProfileUseCase(
        impl : UpdateProfileUseCaseImpl
    ):UpdateProfileUseCase = impl

}