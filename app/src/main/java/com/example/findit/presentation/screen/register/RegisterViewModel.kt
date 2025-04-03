package com.example.findit.presentation.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findit.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) :ViewModel() {

    private val _registerState = MutableStateFlow(RegisterState())
    val registerState: StateFlow<RegisterState> = _registerState

    private val _registerEvent = MutableSharedFlow<RegisterUiEvent>()
    val registerEvent: SharedFlow<RegisterUiEvent> = _registerEvent


    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.NavigateToLoginScreen ->{
               handleLoginNavigation()
            }
            is RegisterEvent.SubmitRegisterForm ->{
                handleRegister(event.firstName, event.lastName, event.phone, event.email, event.password)
            }
        }
    }

    private fun handleLoginNavigation(){
        viewModelScope.launch {
            _registerEvent.emit(RegisterUiEvent.NavigateToLoginScreen)
        }
    }

    private fun handleRegister(firstName : String, lastName : String, phone : String, email : String, password : String) {
        viewModelScope.launch {
//            registerUseCase(firstName, lastName, phone, email, password).
        }
    }
}