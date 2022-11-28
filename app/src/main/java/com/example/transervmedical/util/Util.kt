package com.example.transervmedical.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object Util {
    const val USER_DATABASE = "user_database"
    const val CALENDAR_DATABASE = "calendar_database"
    const val FIREBASE_DATABASE_URL ="https://transervmedical-default-rtdb.europe-west1.firebasedatabase.app/"

    const val HOUR_IN_MILLIS = 60 * 60 * 1000L

    fun Long.formatDateForMapping(): String {
        val sdf = SimpleDateFormat("EEEE d, MMM yyy", Locale.getDefault())
        return sdf.format(this)
    }

    fun Long.formatTime(): String {
        val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
        val sdfNoMinutes = SimpleDateFormat("h a", Locale.getDefault())
        val minutes = SimpleDateFormat("mm", Locale.getDefault()).format(this)
        return if (minutes == "00") sdfNoMinutes.format(this) else sdf.format(this)
    }

    fun Long.formatDate(): String {
        val sdf = SimpleDateFormat("EEE, MMM dd, yyyy", Locale.getDefault())
        return sdf.format(this)
    }

    fun provideCalendarId(): String {
        val random = Random()
        val number = random.nextInt()
        Log.d("Firebase", "number = $number")
        return if (number > 0)
            String.format("%06d", number)
        else
            String.format("%06d", number * -1)
    }

}