package com.example.transervmedical.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.transervmedical.domain.model.Calendar

@Database(
    entities = [Calendar::class],
    version = 1
)
abstract class CalendarDatabase : RoomDatabase() {

    abstract val calendarDao: CalendarDao

}