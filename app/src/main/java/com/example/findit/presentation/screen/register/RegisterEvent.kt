package com.example.findit.presentation.screen.register

sealed class RegisterEvent {
    //aq es shecvale data clasit v
    data class SubmitRegisterForm(val firstName: String, val lastName: String, val phone: String, val email: String, val password: String) : RegisterEvent()
    data object NavigateToLoginScreen : RegisterEvent()
}