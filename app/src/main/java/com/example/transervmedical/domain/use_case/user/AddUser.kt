package com.example.transervmedical.domain.use_case.user

import com.example.transervmedical.domain.model.User
import com.example.transervmedical.domain.repository.UserRepository

class AddUser(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        repository.insertUser(user = user)
    }
}