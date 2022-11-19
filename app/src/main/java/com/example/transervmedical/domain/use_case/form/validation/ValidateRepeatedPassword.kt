package com.example.transervmedical.domain.use_case.form.validation

class ValidateRepeatedPassword {

    fun execute(password: String, repeatedPassword: String): ValidationResult {
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