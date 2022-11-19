package com.example.transervmedical.presentation.registration

data class LoginFormState(
    val email: String = "",
    val emailError: String? = null,
    val emailErrorType: Boolean = false,
    val password: String = "",
    val passwordError: String? = null,
    val passwordErrorType: Boolean = false
)
