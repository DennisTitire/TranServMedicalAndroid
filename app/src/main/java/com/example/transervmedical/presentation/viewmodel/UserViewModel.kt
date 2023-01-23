package com.example.transervmedical.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transervmedical.domain.use_case.form.login.LogInFormEvent
import com.example.transervmedical.domain.use_case.form.register.RegistrationFormEvent
import com.example.transervmedical.domain.use_case.form.register.RegistrationUseCases
import com.example.transervmedical.domain.use_case.form.validation.ValidationEvent
import com.example.transervmedical.presentation.states.LoginFormState
import com.example.transervmedical.presentation.states.RegistrationFormState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val registrationUseCases: RegistrationUseCases,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    var registerState by mutableStateOf(RegistrationFormState())
    var loginState by mutableStateOf(LoginFormState())
    var firebaseError by mutableStateOf("")
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    var rememberMe by mutableStateOf(false)

    fun onEventRegister(registerEvent: RegistrationFormEvent) {
        when (registerEvent) {
            is RegistrationFormEvent.EmailChanged -> {
                registerState = registerState.copy(email = registerEvent.email)
            }
            is RegistrationFormEvent.PhoneNumberChanged -> {
                registerState = registerState.copy(phoneNumber = registerEvent.phoneNumber)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                registerState = registerState.copy(password = registerEvent.password)
            }
            is RegistrationFormEvent.RepeatedPasswordChanged -> {
                registerState =
                    registerState.copy(repeatedPassword = registerEvent.repeatedPasswordChanged)
            }
            is RegistrationFormEvent.Submit -> {
                checkRegisterValidation()
            }
        }
    }

    fun onEventLogin(loginEvent: LogInFormEvent) {
        when (loginEvent) {
            is LogInFormEvent.EmailChanged -> {
                loginState = loginState.copy(email = loginEvent.email)
            }
            is LogInFormEvent.PasswordChanged -> {
                loginState = loginState.copy(password = loginEvent.password)
            }
            is LogInFormEvent.LoginSubmit -> {
                checkLoginValidation()
            }
        }
    }

    private fun checkLoginValidation() {
        val emailResult = registrationUseCases.validateEmail.execute(loginState.email)
        val passwordResult = registrationUseCases.validatePassword.execute(loginState.password)

        val hasLoginError = listOf(
            emailResult,
            passwordResult
        ).any { !it.success }

        loginState = loginState.copy(
            emailError = emailResult.errorMessage,
            emailErrorType = emailResult.errorType,
            passwordError = passwordResult.errorMessage,
            passwordErrorType = passwordResult.errorType
        )
        if (hasLoginError) return

        firebaseAuth.signInWithEmailAndPassword(loginState.email, loginState.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    viewModelScope.launch {
                        validationEventChannel.send(ValidationEvent.Success)
                    }
                } else {
                    viewModelScope.launch {
                        Log.d("Firebase", "${it.exception?.message}")
                        firebaseError = it.exception?.message!!
                        validationEventChannel.send(ValidationEvent.Failure)
                    }
                }
            }
    }

    private fun checkRegisterValidation() {
        val emailResult = registrationUseCases.validateEmail.execute(registerState.email)
        val phoneNumberResult =
            registrationUseCases.validatePhoneNumber.execute(registerState.phoneNumber)
        val passwordResult = registrationUseCases.validatePassword.execute(registerState.password)
        val repeatedPasswordResult =
            registrationUseCases.validateRepeatedPassword.execute(
                registerState.password,
                registerState.repeatedPassword
            )
        val hasRegisterError = listOf(
            emailResult,
            phoneNumberResult,
            passwordResult,
            repeatedPasswordResult
        ).any { !it.success }

        registerState = registerState.copy(
            emailError = emailResult.errorMessage,
            emailErrorType = emailResult.errorType,
            phoneNumberError = phoneNumberResult.errorMessage,
            phoneNumberErrorType = phoneNumberResult.errorType,
            passwordError = passwordResult.errorMessage,
            passwordErrorType = repeatedPasswordResult.errorType,
            repeatedPasswordError = repeatedPasswordResult.errorMessage,
            repeatedPasswordErrorType = repeatedPasswordResult.errorType
        )
        if (hasRegisterError) return

        firebaseAuth.createUserWithEmailAndPassword(registerState.email, registerState.password)
            .addOnCompleteListener { task ->
                viewModelScope.launch {
                    validationEventChannel.send(ValidationEvent.Loading)
                }
                if (task.isSuccessful) {
                    viewModelScope.launch {
                        validationEventChannel.send(ValidationEvent.Success)
                    }
                } else {
                    viewModelScope.launch {
                        Log.d("Firebase", "${task.exception}")
                        firebaseError = task.exception?.message!!
                        validationEventChannel.send(ValidationEvent.Failure)
                    }
                }
            }
    }
}