package com.example.findit.presentation.screen.profile

sealed interface ProfileEvent {
    data object ChangeLanguageClicked : ProfileEvent
}