package com.example.findit.di

import androidx.lifecycle.ViewModel
import com.example.findit.data.repository.LogInRepositoryImpl
import com.example.findit.data.repository.RegisterRepositoryImpl
import com.example.findit.data.repository.UploadImageRepositoryImpl
import com.example.findit.data.repository.UploadPostRepositoryImpl
import com.example.findit.data.repository.UserRepositoryImpl
import com.example.findit.domain.repository.LogInRepository
import com.example.findit.domain.repository.RegisterRepository
import com.example.findit.domain.repository.UploadImageRepository
import com.example.findit.domain.repository.UploadPostRepository
import com.example.findit.domain.repository.UserRepository
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
    abstract fun provideUploadPostRepository(impl: UploadPostRepositoryImpl) : UploadPostRepository

    @Binds
    abstract fun provideUploadImageRepository(impl: UploadImageRepositoryImpl) : UploadImageRepository

    @Binds
    abstract fun provideUserRepository(impl: UserRepositoryImpl) : UserRepository


}