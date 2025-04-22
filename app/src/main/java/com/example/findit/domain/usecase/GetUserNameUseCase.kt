package com.example.findit.domain.usecase

import com.example.findit.domain.repository.UserRepository
import javax.inject.Inject

class GetUserNameUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userid: String): String {
        return userRepository.getUserFullName(userid)
    }

}
