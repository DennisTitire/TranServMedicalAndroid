package com.example.transervmedical.domain.use_case.form.validation

class ValidateRepeatedPassword {

    fun execute(password: String, repeatedPassword: String): ValidationResult {
        if (repeatedPassword.isBlank()) {
            return ValidationResult(
                success = false,
                errorMessage = "The repeated password can't be blank",
                errorType = true
            )
        }
        if (password != repeatedPassword) {
            return ValidationResult(
                success = false,
                errorMessage = "The passwords don't match",
                errorType = true
            )
        }
        return ValidationResult(
            success = true
        )
    }
}