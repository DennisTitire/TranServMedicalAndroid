package com.example.transervmedical.domain.model

data class CalendarEvent(
    val title: String,
    val allDay: Boolean = false,
    val startDay: String,
    val startDayTime: String,
    val endDay: String,
    val endDayTime: String,
    val description: String
)
