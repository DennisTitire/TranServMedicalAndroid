package com.example.transervmedical.presentation.states

data class CalendarState(
    val calendarId: String = "",
    val title: String = "",
    val allDay: Boolean = false,
    var startEvent: Long,
    var endEvent: Long,
    val description: String? = ""
)
