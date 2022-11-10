package com.example.transervmedical.data.repository

import com.example.transervmedical.data.data_source.UserDao
import com.example.transervmedical.domain.model.User
import com.example.transervmedical.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun insertUser(user: User) {
        userDao.insertUser(user = user)
    }

    override suspend fun getUserByEmailAndPassword(email: String, password: String): User {
        return userDao.getUserByEmailAndPassword(email = email, password = password)
    }

}