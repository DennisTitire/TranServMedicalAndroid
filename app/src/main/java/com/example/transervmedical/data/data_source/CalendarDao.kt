package com.example.transervmedical.data.data_source

import androidx.room.*
import com.example.transervmedical.domain.model.Calendar

@Dao
interface CalendarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCalendarEvent(calendar: Calendar)

    @Query("SELECT * FROM calendar")
    suspend fun getAllCalendarEvents(): List<Calendar>

    @Query("SELECT * FROM calendar WHERE calendarId=:calendarId")
    suspend fun getCalendarEvent(calendarId: String): Calendar

    @Update
    suspend fun updateCalendarEvent(calendar: Calendar)

    @Delete
    suspend fun deleteCalendarEvent(calendarId: Calendar)
}