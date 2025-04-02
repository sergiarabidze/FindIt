package com.example.findit.presentation.screen.login

import androidx.lifecycle.ViewModel
import com.example.findit.domain.usecase.LogInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LogInUseCase) : ViewModel() {

    

}