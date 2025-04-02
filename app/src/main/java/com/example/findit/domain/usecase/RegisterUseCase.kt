package com.example.findit.domain.usecase

import com.example.findit.domain.repository.RegisterRepository
import com.example.findit.domain.resource.Resource
import javax.inject.Inject

class RegisterUseCase@Inject constructor(
    private val repository: RegisterRepository
){
    suspend operator fun invoke(email: String, password: String): Resource<Boolean> {
        return repository.registerUser(email, password)
    }
}