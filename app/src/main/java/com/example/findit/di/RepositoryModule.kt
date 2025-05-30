package com.example.findit.di

import com.example.findit.data.repository.GetAllChatsRepositoryImpl
import com.example.findit.data.repository.GetMessageRepositoryImpl
import com.example.findit.data.repository.GetProfilePictureRepositoryImpl
import com.example.findit.data.repository.GetUserProfileRepositoryImpl
import com.example.findit.data.repository.LanguageRepositoryImpl
import com.example.findit.data.repository.LogInRepositoryImpl
import com.example.findit.data.repository.PostsRepositoryImpl
import com.example.findit.data.repository.RegisterRepositoryImpl
import com.example.findit.data.repository.SendMessageRepositoryImpl
import com.example.findit.data.repository.UpdateProfileImageRepositoryImpl
import com.example.findit.data.repository.UpdateUserProfileRepositoryImpl
import com.example.findit.data.repository.UploadImageRepositoryImpl
import com.example.findit.data.repository.UploadPostRepositoryImpl
import com.example.findit.data.repository.UserRepositoryImpl
import com.example.findit.domain.repository.GetAllChatsRepository
import com.example.findit.domain.repository.GetMessagesRepository
import com.example.findit.domain.repository.GetProfilePictureRepository
import com.example.findit.domain.repository.GetUserProfileRepository
import com.example.findit.domain.repository.LanguageRepository
import com.example.findit.domain.repository.LogInRepository
import com.example.findit.domain.repository.PostsRepository
import com.example.findit.domain.repository.RegisterRepository
import com.example.findit.domain.repository.SendMessageRepository
import com.example.findit.domain.repository.UpdateProfileImageRepository
import com.example.findit.domain.repository.UpdateUserProfileRepository
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
    abstract fun provideLanguageRepository(impl : LanguageRepositoryImpl): LanguageRepository
    @Binds
    abstract fun provideUploadPostRepository(impl: UploadPostRepositoryImpl) : UploadPostRepository

    @Binds
    abstract fun provideUploadImageRepository(impl: UploadImageRepositoryImpl) : UploadImageRepository

    @Binds
    abstract fun provideUserRepository(impl: UserRepositoryImpl) : UserRepository


    @Binds
    abstract fun provideGetUserProfileRepository(impl : GetUserProfileRepositoryImpl): GetUserProfileRepository

    @Binds
    abstract fun provideUpdateUserProfileRepository(impl : UpdateUserProfileRepositoryImpl) : UpdateUserProfileRepository

    @Binds
    abstract fun provideGetPostsRepository(impl : PostsRepositoryImpl) : PostsRepository

    @Binds
    abstract fun provideUpdateProfileImageRepository(impl: UpdateProfileImageRepositoryImpl) : UpdateProfileImageRepository

    @Binds
    abstract fun provideGetAllChatsRepository(impl : GetAllChatsRepositoryImpl) : GetAllChatsRepository

    @Binds
    abstract fun provideGetMessagesRepository(impl : GetMessageRepositoryImpl) : GetMessagesRepository

    @Binds
    abstract fun provideSendMessageRepository(impl : SendMessageRepositoryImpl) : SendMessageRepository
    @Binds
    abstract fun provideGetProfilePictureRepository(impl: GetProfilePictureRepositoryImpl) : GetProfilePictureRepository
}