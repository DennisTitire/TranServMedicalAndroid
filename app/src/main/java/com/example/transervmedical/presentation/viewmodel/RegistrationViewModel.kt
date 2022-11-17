package com.example.transervmedical.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transervmedical.domain.model.User
import com.example.transervmedical.domain.use_case.form.register.RegistrationFormEvent
import com.example.transervmedical.domain.use_case.form.register.RegistrationUseCases
import com.example.transervmedical.domain.use_case.user.UserUseCases
import com.example.transervmedical.presentation.registration.RegistrationFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCases: RegistrationUseCases,
    private val userUseCases: UserUseCases
) : ViewModel() {

    var state by mutableStateOf(RegistrationFormState())
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()


    fun onEvent(event: RegistrationFormEvent) {
        when (event) {
            is RegistrationFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is RegistrationFormEvent.PhoneNumberChanged -> {
                state = state.copy(phoneNumber = event.phoneNumber)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegistrationFormEvent.RepeatedPasswordChanged -> {
                state = state.copy(repeatedPassword = event.repeatedPasswordChanged)
            }
            is RegistrationFormEvent.Submit -> {
                submitUser()
            }
        }
    }

    private fun submitUser() {
        val emailResult = registrationUseCases.validateEmail.execute(state.email)
        val phoneNumberResult = registrationUseCases.validatePhoneNumber.execute(state.phoneNumber)
        val passwordResult = registrationUseCases.validatePassword.execute(state.password)
        val repeatedPasswordResult =
            registrationUseCases.validateRepeatedPassword.execute(
                state.password,
                state.repeatedPassword
            )
        val hasError = listOf(
            emailResult,
            phoneNumberResult,
            passwordResult,
            repeatedPasswordResult
        ).any { !it.success }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                phoneNumberError = phoneNumberResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatedPasswordResult.errorMessage
            )
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    fun addUserInDb(user: User) {
        viewModelScope.launch {
            userUseCases.addUserUseCase(user = user)
        }
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }
}