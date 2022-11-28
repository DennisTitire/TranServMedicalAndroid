package com.example.transervmedical.domain.model

data class Calendar(
    val calendarId: String = "",
    val title: String = "",
    val allDay: Boolean = false,
    var startEvent: Long? = null,
    var endEvent: Long? = null,
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

