package com.example.findit.presentation.screen.editprofile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findit.domain.model.UserProfile
import com.example.findit.domain.resource.Resource
import com.example.findit.domain.usecase.GetProfileUseCase
import com.example.findit.domain.usecase.UpdateProfileImageUrlUseCase
import com.example.findit.domain.usecase.UpdateProfileUseCase
import com.example.findit.domain.usecase.UploadProfilePhotoUseCase
import com.example.findit.presentation.mappper.toPresentation
import com.example.findit.presentation.model.UserProfilePresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.IOException
import javax.inject.Inject
@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val uploadProfilePhotoUseCase: UploadProfilePhotoUseCase,
    private val updateProfileImageUrlUseCase: UpdateProfileImageUrlUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditProfileState())
    val state: StateFlow<EditProfileState> = _state

    private val _effect = MutableSharedFlow<EditProfileEffect>()
    val effect: SharedFlow<EditProfileEffect> = _effect

    fun onEvent(event: EditProfileEvent) {
        when (event) {
            is EditProfileEvent.LoadProfile -> loadProfile()
            is EditProfileEvent.OnSaveClicked -> saveProfile()
            is EditProfileEvent.OnProfileImageUriSelected -> compressImage(event.context, event.uri)
        }
    }

    fun updateUserProfileData(updatedProfile: UserProfilePresentation) {
        _state.update { it.copy(userProfile = updatedProfile) }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            getProfileUseCase().collect { result ->
                when (result) {
                    is Resource.Loader -> _state.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> {
                        val presentation = result.data.toPresentation()
                        _state.update { it.copy(userProfile = presentation, isLoading = false) }
                    }
                    is Resource.Error -> _effect.emit(EditProfileEffect.ShowError(result.errorMessage))
                }
            }
        }
    }

    private fun saveProfile() {
        viewModelScope.launch {
            val profile = state.value.userProfile ?: return@launch
            val user = UserProfile(
                name = profile.name,
                surname = profile.surname,
                phone = profile.phone,
                email = profile.email,
                password = profile.password,
                profileImageUrl = profile.profileImageUrl
            )
            updateProfileUseCase(user).collect { result ->
                when (result) {
                    is Resource.Loader -> _state.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> _effect.emit(EditProfileEffect.ProfileSaved)
                    is Resource.Error -> _effect.emit(EditProfileEffect.ShowError(result.errorMessage))
                }
            }
        }
    }

    private fun compressImage(context: Context, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val options = BitmapFactory.Options().apply {
                        inSampleSize = 2
                        inJustDecodeBounds = false
                    }

                    val originalBitmap = BitmapFactory.decodeStream(inputStream, null, options)
                        ?: throw IOException("Failed to decode bitmap")

                    val outputStream = ByteArrayOutputStream().apply {
                        if (!originalBitmap.compress(Bitmap.CompressFormat.JPEG, 80, this)) {
                            throw IOException("Failed to compress bitmap")
                        }
                    }

                    val compressedBitmap = BitmapFactory.decodeByteArray(
                        outputStream.toByteArray(),
                        0,
                        outputStream.size()
                    )

                    _state.update { it.copy(profileBitmap = compressedBitmap) }
                    uploadProfileImage(compressedBitmap)
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false) }
                _effect.emit(EditProfileEffect.ShowError(e.localizedMessage ?: "Image compression failed"))
            }
        }
    }

    private fun uploadProfileImage(bitmap: Bitmap) {
        viewModelScope.launch {
            uploadProfilePhotoUseCase(bitmap).collect { result ->
                when (result) {
                    is Resource.Success -> updateProfileImageUrl(result.data)
                    is Resource.Error -> _effect.emit(EditProfileEffect.ShowError(result.errorMessage))
                    is Resource.Loader -> _state.update { it.copy(isLoading = result.isLoading) }
                }
            }
        }
    }

    private fun updateProfileImageUrl(url: String) {
        viewModelScope.launch {
            updateProfileImageUrlUseCase(url).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.update { currentState ->
                            currentState.copy(
                                userProfile = currentState.userProfile?.copy(profileImageUrl = url)
                            )
                        }
                        _effect.emit(EditProfileEffect.ProfileImageUpdated(url))
                    }
                    is Resource.Error -> _effect.emit(EditProfileEffect.ShowError(result.errorMessage))
                    is Resource.Loader -> _state.update { it.copy(isLoading = result.isLoading) }
                }
            }
        }
    }


}
