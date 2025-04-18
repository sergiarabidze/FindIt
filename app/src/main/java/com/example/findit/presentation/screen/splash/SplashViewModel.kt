package com.example.findit.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findit.domain.usecase.GetAppLanguageUseCase
import com.example.findit.domain.usecase.GetCurrentUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAppLanguageUseCase: GetAppLanguageUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel() {

    private val _effect = MutableSharedFlow<SplashEffect>()
    val effect: SharedFlow<SplashEffect> = _effect

    init {
        checkLanguage()
        checkUser()
    }

    private fun checkLanguage() {
        viewModelScope.launch {
            val language = getAppLanguageUseCase()
            _effect.emit(SplashEffect.SetLocale(language))
        }
    }

    private fun checkUser() {
        viewModelScope.launch {
            val user = getCurrentUserIdUseCase.invoke()
           if(user == null){
               _effect.emit(SplashEffect.NavigateToLoginScreen)
           }else{
               _effect.emit(SplashEffect.NavigateToHomeScreen)
           }
        }
    }
}