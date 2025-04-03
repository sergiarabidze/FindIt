package com.example.findit.presentation.screen.login

sealed class LoginEvent {
    data object NavigateToRegisterScreen : LoginEvent()
    data class  SubmitLoginForm(val email :String, val password : String) : LoginEvent()
}