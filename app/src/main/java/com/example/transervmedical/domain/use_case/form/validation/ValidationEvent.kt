package com.example.transervmedical.domain.use_case.form.validation

sealed class ValidationEvent {
    object Success : ValidationEvent()
    object Loading : ValidationEvent()
    object Failure : ValidationEvent()

}