package com.example.transervmedical.presentation.registration

data class RegistrationFormState(
    val email: String = "",
    val emailError: String? = null,
    val emailErrorType: Boolean = false,
    val phoneNumber: String = "",
    val phoneNumberError: String? = null,
    val phoneNumberErrorType: Boolean = false,
    val password: String = "",
    val passwordError: String? = null,
    val passwordErrorType: Boolean = false,
    val repeatedPassword: String = "",
    val repeatedPasswordError: String? = null,
    val repeatedPasswordErrorType: Boolean = false
)
