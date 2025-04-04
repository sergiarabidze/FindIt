package com.example.findit.domain.usecase

import android.util.Log.d
import com.example.findit.domain.repository.RegisterRepository
import com.example.findit.domain.resource.RegisterForm
import com.example.findit.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase@Inject constructor(
    private val repository: RegisterRepository
){
    suspend operator fun invoke(registerForm: RegisterForm): Flow<Resource<Boolean>> {
        d("TAG", "invoke: $registerForm")
        return repository.registerUser(registerForm)
    }
}