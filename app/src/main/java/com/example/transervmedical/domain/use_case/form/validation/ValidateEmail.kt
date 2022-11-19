package com.example.transervmedical.domain.use_case.form.validation

import android.util.Patterns

class ValidateEmail {

    fun execute(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                success = false,
                errorMessage = "The email can't be blank",
                errorType = true
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                success = false,
                errorMessage = "That's not a valid email",
                errorType = true
            )
        }
        return ValidationResult(
            success = true
        )
    }
}