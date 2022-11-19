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
import com.example.transervmedical.presentation.registration.LoginFormState
import com.example.transervmedical.presentation.registration.RegistrationFormState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val registrationUseCases: RegistrationUseCases,
//    private val userUseCases: UserUseCases,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    var registerState by mutableStateOf(RegistrationFormState())
    var loginState by mutableStateOf(LoginFormState())
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

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

    fun registerUserToRealtimeDatabase() {

    }

    fun registerUserToFirebase(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("Firebase", "fail: ${it.result}")
            } else {
                Log.d("Firebase", "fail: ${it.result}")
            }
        }
    }

    fun signInUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
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

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
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

        if (hasRegisterError) {
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
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

//    fun addUserInDb(user: User) {
//        viewModelScope.launch {
//            userUseCases.addUserUseCase(user = user)
//        }
//    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
//        object Loading : ValidationEvent()
//        object Failure : ValidationEvent()
    }
}