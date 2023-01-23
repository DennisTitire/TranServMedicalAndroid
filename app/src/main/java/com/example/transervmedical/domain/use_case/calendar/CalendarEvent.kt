package com.example.transervmedical.domain.use_case.calendar

import com.example.transervmedical.domain.model.Calendar

sealed class CalendarEvent {
    data class TitleChanged(val title: String) : CalendarEvent()
    data class AllDay(val allDay: Boolean) : CalendarEvent()
    data class StartEvent(val startEvent: Long) : CalendarEvent()
    data class EndEvent(val endEvent: Long) : CalendarEvent()
    data class DescriptionChanged(val description: String) : CalendarEvent()
    data class UpdateCalendarEvent(val calendar: Calendar) : CalendarEvent()
    data class DeleteCalendarEvent(val calendarId: Calendar) : CalendarEvent()

    object AddCalendarEvent : CalendarEvent()
}