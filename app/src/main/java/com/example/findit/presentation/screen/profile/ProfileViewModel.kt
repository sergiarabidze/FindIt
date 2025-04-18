package com.example.findit.presentation.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findit.domain.usecase.SetAppLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val setAppLanguage: SetAppLanguageUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state

    private val _effect = MutableSharedFlow<ProfileEffect>()
    val effect: SharedFlow<ProfileEffect> = _effect

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.ChangeLanguageClicked -> {
                changeAppLanguage()
            }
            is ProfileEvent.EditProfileClicked -> {
                navigateToEditProfile()
            }
        }
    }

    private fun changeAppLanguage() {
        viewModelScope.launch {
            val newLang = if (Locale.getDefault().language == "en") "ka" else "en"
            setAppLanguage(newLang)
            _effect.emit(ProfileEffect.ChangeLanguage(newLang))
        }
    }

    private fun navigateToEditProfile() {
        viewModelScope.launch {
            _effect.emit(ProfileEffect.NavigateToEditProfile)
        }
    }
}
