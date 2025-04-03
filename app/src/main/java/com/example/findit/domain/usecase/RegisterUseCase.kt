package com.example.findit.domain.usecase

import com.example.findit.domain.repository.RegisterRepository
import com.example.findit.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase@Inject constructor(
    private val repository: RegisterRepository
){
    suspend operator fun invoke(email: String, password: String): Flow<Resource<Boolean>> {
        return repository.registerUser(email, password)
    }
}