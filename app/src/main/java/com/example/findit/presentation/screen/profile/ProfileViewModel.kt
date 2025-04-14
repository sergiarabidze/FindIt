package com.example.findit.presentation.screen.profile

import android.content.res.Configuration
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale
class ProfileViewModel : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state

    private val _effect = MutableSharedFlow<ProfileEffect>()
    val effect: SharedFlow<ProfileEffect> = _effect

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.ChangeLanguageClicked -> {
                sendEffect(ProfileEffect.ChangeLanguage)
            }
        }
    }

    private fun sendEffect(effect: ProfileEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}
