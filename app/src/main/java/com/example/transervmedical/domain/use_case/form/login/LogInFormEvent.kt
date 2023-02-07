package com.example.transervmedical.domain.use_case.form.login

sealed class LogInFormEvent {
    data class EmailChanged(val email: String) : LogInFormEvent()
    data class PasswordChanged(val password: String) : LogInFormEvent()
    data class RememberMeChanged(val rememberMe: Boolean) : LogInFormEvent()
    data class ForgotPassword(val email: String) : LogInFormEvent()
    object ForgotPasswordSubmit : LogInFormEvent()
    object LoginSubmit : LogInFormEvent()
}
