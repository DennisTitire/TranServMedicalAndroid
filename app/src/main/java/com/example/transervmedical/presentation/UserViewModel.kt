package com.example.transervmedical.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transervmedical.domain.model.User
import com.example.transervmedical.domain.use_case.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

     fun addUser(user: User) {
        viewModelScope.launch {
            userUseCases.addUserUseCase(user = user)
        }
    }

    suspend fun getUser(email: String, password: String): User {
        return userUseCases.getUserUseCase(email, password)
    }
}