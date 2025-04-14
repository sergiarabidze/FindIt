package com.example.findit.presentation.screen.profile

sealed interface ProfileEffect {
    data object NavigateToLogin : ProfileEffect
    data object NavigateToMyProfile : ProfileEffect
    data object NavigateToEditProfile : ProfileEffect
    data object ChangeLanguage : ProfileEffect
    data object NavigateToChangeTheme : ProfileEffect
}
