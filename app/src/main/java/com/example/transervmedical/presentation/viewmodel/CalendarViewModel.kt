package com.example.transervmedical.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.transervmedical.domain.model.Calendar
import com.example.transervmedical.domain.use_case.calendar.CalendarEvent
import com.example.transervmedical.presentation.states.CalendarState
import com.example.transervmedical.util.Util.HOUR_IN_MILLIS
import com.example.transervmedical.util.Util.provideCalendarId
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val database: FirebaseDatabase,
) : ViewModel() {

    var calendarState by mutableStateOf(CalendarState(
        startEvent = System.currentTimeMillis() + HOUR_IN_MILLIS,
        endEvent = System.currentTimeMillis() + 2 * HOUR_IN_MILLIS
    ))
    private val userId = firebaseAuth.currentUser?.uid
    private val databaseRef = database.getReference("calendarEvents")
    var calendarEventList = ArrayList<Calendar?>()

    fun onCalendarEvent(calendarEvent: CalendarEvent) {
        when (calendarEvent) {
            is CalendarEvent.TitleChanged -> {
                calendarState = calendarState.copy(title = calendarEvent.title)
            }
            is CalendarEvent.AllDay -> {
                calendarState = calendarState.copy(allDay = calendarEvent.allDay)
            }
            is CalendarEvent.StartEvent -> {
                calendarState = calendarState.copy(startEvent = calendarEvent.startEvent)
            }
            is CalendarEvent.EndEvent -> {
                calendarState = calendarState.copy(endEvent = calendarEvent.endEvent)
            }
            is CalendarEvent.DescriptionChanged -> {
                calendarState = calendarState.copy(description = calendarEvent.description)
            }
            is CalendarEvent.AddCalendarEvent -> {
                addCalendarEvent()
                getDatabaseData()
            }
        }
    }

    private fun addCalendarEvent() {
        // Working
        val calendarId = provideCalendarId()
        val calendarObject: Calendar
        if (!calendarState.allDay) {
            calendarObject = Calendar(
                calendarId = calendarId,
                title = calendarState.title,
                startEvent = calendarState.startEvent,
                endEvent = calendarState.endEvent,
                allDay = calendarState.allDay,
                description = calendarState.description
            )
        } else {
            calendarObject = Calendar(
                calendarId = calendarId,
                title = calendarState.title,
                startEvent = 0,
                endEvent = 0,
                allDay = calendarState.allDay,
                description = calendarState.description
            )
        }
        databaseRef.child("$userId-$calendarId").setValue(calendarObject)
            .addOnCompleteListener {
                Log.d("Firebase", "Complete Message: ${it.exception}")
            }.addOnFailureListener {
                Log.d("Firebase", "Failure Message: ${it.message}")
            }
    }

    fun getDatabaseData() {
        // snapshot data receive
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    val calendar = data.getValue(Calendar::class.java)
                    calendarEventList.add(calendar)
                    /*
                    Log.d("Firebase", "Get calendarObj: $calendarStateWithoutLocal")
                    Log.d("Firebase", "Get calendarObj.id: ${calendarStateWithoutLocal?.calendarId}")
                    Log.d("Firebase", "Get calendarObj.allDay: ${calendarStateWithoutLocal?.allDay}")
                    Log.d("Firebase", "Get calendarObj.title: ${calendarStateWithoutLocal?.title}")
                    Log.d("Firebase", "Get calendarObj.desc: ${calendarStateWithoutLocal?.description}")
                     */
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase", "Error: ${error.message}")
            }
        })
    }
}