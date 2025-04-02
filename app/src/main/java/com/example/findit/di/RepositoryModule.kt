package com.example.findit.di

import androidx.lifecycle.ViewModel
import com.example.findit.data.repository.LogInRepositoryImpl
import com.example.findit.data.repository.RegisterRepositoryImpl
import com.example.findit.domain.repository.LogInRepository
import com.example.findit.domain.repository.RegisterRepository
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


}