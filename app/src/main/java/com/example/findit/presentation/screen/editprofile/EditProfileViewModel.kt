package com.example.findit.presentation.screen.editprofile


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findit.domain.model.UserProfile
import com.example.findit.domain.resource.Resource
import com.example.findit.domain.usecase.GetProfileUseCase
import com.example.findit.domain.usecase.UpdateProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditProfileState())
    val state: StateFlow<EditProfileState> = _state

    private val _effect = MutableSharedFlow<EditProfileEffect>()
    val effect: SharedFlow<EditProfileEffect> = _effect

    fun onEvent(event: EditProfileEvent) {
        when (event) {
            is EditProfileEvent.LoadProfile -> loadProfile()
            is EditProfileEvent.OnNameChanged -> _state.update { it.copy(name = event.value) }
            is EditProfileEvent.OnSurnameChanged -> _state.update { it.copy(surname = event.value) }
            is EditProfileEvent.OnPhoneChanged -> _state.update { it.copy(phone = event.value) }
            is EditProfileEvent.OnEmailChanged -> _state.update { it.copy(email = event.value) }
            is EditProfileEvent.OnPasswordChanged -> _state.update { it.copy(password = event.value) }
            is EditProfileEvent.OnSaveClicked -> saveProfile()
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            getProfileUseCase().collect { result ->
                when (result) {
                    is Resource.Loader -> _state.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> _state.update {
                        it.copy(
                            name = result.data.name,
                            surname = result.data.surname,
                            phone = result.data.phone,
                            email = result.data.email,
                            password = result.data.password,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> _effect.emit(EditProfileEffect.ShowError(result.errorMessage))
                }
            }
        }
    }

    private fun saveProfile() {
        viewModelScope.launch {
            val user = UserProfile(
                name = state.value.name,
                surname = state.value.surname,
                phone = state.value.phone,
                email = state.value.email,
                password = state.value.password
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
}