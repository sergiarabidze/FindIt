package com.example.findit.presentation.screen.profile

data class ProfileState(
    val fullName: String = "",
    val profileImageUrl: String? = null,
    val isLoading: Boolean = false
)

