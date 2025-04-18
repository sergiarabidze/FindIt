package com.example.findit.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findit.domain.usecase.GetAppLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAppLanguageUseCase: GetAppLanguageUseCase
) : ViewModel() {

    private val _effect = MutableSharedFlow<SplashEffect>()
    val effect: SharedFlow<SplashEffect> = _effect

    init {
        checkLanguage()
    }

    private fun checkLanguage() {
        viewModelScope.launch {
            val language = getAppLanguageUseCase()
            _effect.emit(SplashEffect.SetLocale(language))
        }
    }
}