package com.example.transervmedical.presentation.states

data class CalendarState(
    val calendarId: String = "",
    val title: String = "",
    val allDay: Boolean = false,
    var startEvent: Long? = null,
    var endEvent: Long? = null,
    val description: String? = ""
)
