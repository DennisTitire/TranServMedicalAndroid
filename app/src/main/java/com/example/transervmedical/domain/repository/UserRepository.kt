package com.example.transervmedical.domain.repository

import com.example.transervmedical.domain.model.User

interface UserRepository {

    suspend fun insertUser(user: User)

    suspend fun getUserByEmailAndPassword(email: String, password: String): User

}