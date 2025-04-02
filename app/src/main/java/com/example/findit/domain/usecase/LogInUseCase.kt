package com.example.findit.domain.usecase

import com.example.findit.domain.repository.LogInRepository
import com.example.findit.domain.resource.Resource
import javax.inject.Inject

class LogInUseCase@Inject constructor(
    private val repository: LogInRepository
){

    suspend operator fun invoke(email: String, password: String): Resource<Boolean> {

        return repository.loginUser(email, password)
        
    }

}

