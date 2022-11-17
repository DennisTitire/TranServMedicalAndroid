package com.example.transervmedical.domain.use_case.form.register

sealed class RegistrationFormEvent {
    data class EmailChanged(val email: String) : RegistrationFormEvent()
    data class PhoneNumberChanged(val phoneNumber: String) : RegistrationFormEvent()
    data class PasswordChanged(val password: String) : RegistrationFormEvent()
    data class RepeatedPasswordChanged(val repeatedPasswordChanged: String) : RegistrationFormEvent()

    object Submit : RegistrationFormEvent()
}
