package com.example.transervmedical.domain.use_case.form.validation

class ValidatePhoneNumber {

    fun execute(phoneNumber: String): ValidationResult {
        if (phoneNumber.length < 10) {
            return ValidationResult(
                success = false,
                errorMessage = "The phone number needs to contain 10 digits",
                errorType = true
            )
        }
        val contains = phoneNumber.contains("07")
        if (!contains) {
            return ValidationResult(
                success = false,
                errorMessage = "The phone number needs to start with 07",
                errorType = true
            )
        }
        return ValidationResult(
            success = true
        )
    }
}