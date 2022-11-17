package com.example.transervmedical.domain.use_case.form.register

import com.example.transervmedical.domain.use_case.form.validation.ValidateEmail
import com.example.transervmedical.domain.use_case.form.validation.ValidatePassword
import com.example.transervmedical.domain.use_case.form.validation.ValidatePhoneNumber
import com.example.transervmedical.domain.use_case.form.validation.ValidateRepeatedPassword

data class RegistrationUseCases(
    val validateEmail: ValidateEmail,
    val validatePhoneNumber: ValidatePhoneNumber,
    val validatePassword: ValidatePassword,
    val validateRepeatedPassword: ValidateRepeatedPassword
)
