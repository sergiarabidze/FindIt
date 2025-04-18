package com.example.findit.data.repository

import com.example.findit.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : UserRepository {

    override fun getCurrentUserId(): String? {

        return auth.currentUser?.uid

    }

}