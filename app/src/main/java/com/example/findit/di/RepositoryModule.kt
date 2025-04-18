package com.example.findit.di

import androidx.lifecycle.ViewModel
import com.example.findit.data.repository.GetUserProfileRepositoryImpl
import com.example.findit.data.repository.LanguageRepositoryImpl
import com.example.findit.data.repository.LogInRepositoryImpl
import com.example.findit.data.repository.RegisterRepositoryImpl
import com.example.findit.data.repository.UpdateUserProfileRepositoryImpl
import com.example.findit.domain.repository.GetUserProfileRepository
import com.example.findit.domain.repository.LanguageRepository
import com.example.findit.domain.repository.LogInRepository
import com.example.findit.domain.repository.RegisterRepository
import com.example.findit.domain.repository.UpdateUserProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideLogInRepository(impl: LogInRepositoryImpl) : LogInRepository

    @Binds
    abstract fun provideRegisterRepository(impl: RegisterRepositoryImpl) : RegisterRepository

    @Binds
    abstract fun provideLanguageRepository(impl : LanguageRepositoryImpl): LanguageRepository

    @Binds
    abstract fun provideGetUserProfileRepository(impl : GetUserProfileRepositoryImpl): GetUserProfileRepository

    @Binds
    abstract fun provideUpdateUserProfileRepository(impl : UpdateUserProfileRepositoryImpl) : UpdateUserProfileRepository
}