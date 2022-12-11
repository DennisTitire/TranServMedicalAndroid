package com.example.transervmedical.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.transervmedical.domain.model.Calendar

@Dao
interface CalendarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCalendarEvent(calendar: Calendar)

    @Query("SELECT * FROM calendar")
    suspend fun getAllCalendarEvents(): List<Calendar>
}