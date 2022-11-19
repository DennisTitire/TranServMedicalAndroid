package com.example.transervmedical.domain.use_case.form.validation

class ValidatePassword {

    fun execute(password: String): ValidationResult {
        if (password.length < 8) {
            return ValidationResult(
                success = false,
                errorMessage = "The password needs to consist at least 8 characters",
                errorType = true
            )
        }
        val containsLettersAndDigits = password.any { it.isDigit() } && password.any { it.isLetter() }
        if (!containsLettersAndDigits) {
            return ValidationResult(
                success = false,
                errorMessage = "The password needs to contain at least one letter and digit",
                errorType = true
            )
        }
        return ValidationResult(
            success = true
        )
    }
}