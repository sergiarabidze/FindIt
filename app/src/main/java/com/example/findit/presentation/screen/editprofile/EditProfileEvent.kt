package com.example.findit.presentation.screen.editprofile

sealed interface EditProfileEvent {
    data object LoadProfile : EditProfileEvent
    data class OnNameChanged(val value: String) : EditProfileEvent
    data class OnSurnameChanged(val value: String) : EditProfileEvent
    data class OnPhoneChanged(val value: String) : EditProfileEvent
    data class OnEmailChanged(val value: String) : EditProfileEvent
    data class OnPasswordChanged(val value: String) : EditProfileEvent
    data object OnSaveClicked : EditProfileEvent
}