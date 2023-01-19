package com.example.transervmedical.presentation.states

import com.example.transervmedical.util.Util.HOUR_IN_MILLIS

data class CalendarState(
    val calendarId: String = "",
    var title: String = "",
    var allDay: Boolean = false,
    var startEvent: Long = System.currentTimeMillis() + HOUR_IN_MILLIS,
    var endEvent: Long = System.currentTimeMillis() + 2 * HOUR_IN_MILLIS,
    var description: String? = "",
)
