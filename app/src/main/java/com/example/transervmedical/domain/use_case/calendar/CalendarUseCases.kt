package com.example.transervmedical.domain.use_case.calendar

data class CalendarUseCases(
    val addCalendarEvent: AddCalendarEvent,
    val getAllCalendarEvents: GetAllCalendarEvents,
    val getCalendarEvent: GetCalendarEvent
)
