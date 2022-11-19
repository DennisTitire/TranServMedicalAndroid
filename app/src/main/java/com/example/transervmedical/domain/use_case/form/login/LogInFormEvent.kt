package com.example.transervmedical.domain.use_case.form.login

sealed class LogInFormEvent {
    data class EmailChanged(val email: String) : LogInFormEvent()
    data class PasswordChanged(val password: String) : LogInFormEvent()

    object LoginSubmit : LogInFormEvent()
}
