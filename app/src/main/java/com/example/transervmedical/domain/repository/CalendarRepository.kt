package com.example.transervmedical.domain.repository

import com.example.transervmedical.domain.model.Calendar

interface CalendarRepository {

    suspend fun addCalendarEvent(calendarEvent: Calendar)

    suspend fun getAllCalendarEvents(): List<Calendar>

}