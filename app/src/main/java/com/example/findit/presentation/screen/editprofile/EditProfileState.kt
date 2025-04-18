package com.example.findit.presentation.screen.editprofile

data class EditProfileState(
    val name: String = "",
    val surname: String = "",
    val phone: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false
)