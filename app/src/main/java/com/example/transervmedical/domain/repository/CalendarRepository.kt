package com.example.transervmedical.domain.repository

import com.example.transervmedical.domain.model.Calendar

interface CalendarRepository {

    suspend fun addCalendarEvent(calendarEvent: Calendar)

    suspend fun getAllCalendarEvents(): List<Calendar>

    suspend fun getCalendarEvent(calendarId: String): Calendar

    suspend fun updateCalendarEvent(calendar: Calendar)

    suspend fun deleteCalendarEvent(calendarId: Calendar)

}