package com.example.findit.presentation.screen.splash

sealed interface SplashEffect {
    data class SetLocale(val languageCode: String) : SplashEffect
}