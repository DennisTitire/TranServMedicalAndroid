package com.example.transervmedical.domain.use_case.calendar

import com.example.transervmedical.domain.model.Calendar
import com.example.transervmedical.domain.repository.CalendarRepository
import javax.inject.Inject

class GetCalendarEvent @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(calendarId: String): Calendar {
        return calendarRepository.getCalendarEvent(calendarId)
    }
}