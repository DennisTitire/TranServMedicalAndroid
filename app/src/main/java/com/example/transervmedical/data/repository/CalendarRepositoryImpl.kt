package com.example.transervmedical.data.repository

import com.example.transervmedical.data.data_source.CalendarDao
import com.example.transervmedical.domain.model.Calendar
import com.example.transervmedical.domain.repository.CalendarRepository

class CalendarRepositoryImpl(
    private val calendarDao: CalendarDao,
) : CalendarRepository {

    override suspend fun addCalendarEvent(calendarEvent: Calendar) {
        return calendarDao.addCalendarEvent(calendarEvent)
    }

    override suspend fun getAllCalendarEvents(): List<Calendar> {
        return calendarDao.getAllCalendarEvents()
    }
}