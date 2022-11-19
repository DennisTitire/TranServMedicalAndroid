package com.example.transervmedical.domain.use_case.form.validation

data class ValidationResult(
    val success: Boolean,
    val errorMessage: String? = null,
    val errorType: Boolean = false
)
