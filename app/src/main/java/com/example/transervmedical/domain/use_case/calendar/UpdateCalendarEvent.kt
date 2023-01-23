package com.example.transervmedical.domain.use_case.calendar

import com.example.transervmedical.domain.model.Calendar
import com.example.transervmedical.domain.repository.CalendarRepository
import javax.inject.Inject

class UpdateCalendarEvent @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(calendar: Calendar) {
        calendarRepository.updateCalendarEvent(calendar)
    }
}