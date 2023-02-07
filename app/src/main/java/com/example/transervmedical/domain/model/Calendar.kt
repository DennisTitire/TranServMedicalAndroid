package com.example.transervmedical.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.Exclude

@Entity
data class Calendar(
    @PrimaryKey(autoGenerate = false)
    val calendarId: String,
    val userName: String,
    var title: String,
    val allDay: Boolean,
    var startEvent: Long,
    var endEvent: Long,
    val description: String?
) {
    constructor() : this(
        calendarId = "",
        userName = "",
        title = "",
        allDay = false,
        startEvent = System.currentTimeMillis(),
        endEvent = System.currentTimeMillis(),
        description = ""
    )

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "calendarId" to calendarId,
            "userName" to userName,
            "title" to title,
            "allDay" to allDay,
            "startEvent" to startEvent,
            "endEvent" to endEvent,
            "description" to description
        )
    }
}

