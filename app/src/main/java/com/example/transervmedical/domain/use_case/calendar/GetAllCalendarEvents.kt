package com.example.transervmedical.domain.use_case.calendar

import com.example.transervmedical.domain.model.Calendar
import com.example.transervmedical.domain.repository.CalendarRepository
import com.example.transervmedical.util.Util.formatDateForMapping
import javax.inject.Inject

class GetAllCalendarEvents @Inject constructor(
    private val calendarRepository: CalendarRepository,
) {
    suspend operator fun invoke(): Map<String, List<Calendar>> {
        return calendarRepository.getAllCalendarEvents().groupBy { event ->
            event.startEvent?.formatDateForMapping() ?: "Nu avem ce face"
        }
    }
}