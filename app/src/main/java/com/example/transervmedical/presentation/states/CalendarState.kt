package com.example.transervmedical.presentation.states

import com.example.transervmedical.util.Util

data class CalendarState(
    val calendarId: String = "",
    var title: String = "",
    var allDay: Boolean = false,
    var startEvent: Long = System.currentTimeMillis() + Util.HOUR_IN_MILLIS,
    var endEvent: Long = System.currentTimeMillis() + 2 * Util.HOUR_IN_MILLIS,
    var description: String? = "",
)
