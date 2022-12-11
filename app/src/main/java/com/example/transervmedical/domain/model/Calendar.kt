package com.example.transervmedical.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Calendar(
    @PrimaryKey(autoGenerate = false)
    val calendarId: String = "",
    val title: String = "",
    val allDay: Boolean = false,
    var startEvent: Long?,
    var endEvent: Long?,
    val description: String? = ""
) {
    constructor() : this(
        calendarId = "",
        title = "",
        allDay = false,
        startEvent = null,
        endEvent = null,
        description = ""
    )
}

