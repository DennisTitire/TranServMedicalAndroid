package com.example.transervmedical.domain.use_case

import com.example.transervmedical.domain.model.User
import com.example.transervmedical.domain.repository.UserRepository
import com.example.transervmedical.presentation.exceptions.InvalidUserException

class AddUser(
    private val repository: UserRepository
) {
    @Throws(InvalidUserException::class)
    suspend operator fun invoke(user: User) {
        if (user.email.isBlank()) {
            throw InvalidUserException("The email can't be empty")
        }
        if (user.password.isBlank()) {
            throw InvalidUserException("The password can't be empty")
        }
        repository.insertUser(user = user)
    }
}