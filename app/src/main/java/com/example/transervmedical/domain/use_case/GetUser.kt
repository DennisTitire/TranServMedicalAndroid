package com.example.transervmedical.domain.use_case

import com.example.transervmedical.domain.model.User
import com.example.transervmedical.domain.repository.UserRepository

class GetUser(
    private val repository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): User {
        return repository.getUserByEmailAndPassword(email, password)
    }
}