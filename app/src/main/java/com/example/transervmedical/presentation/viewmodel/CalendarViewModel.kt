package com.example.transervmedical.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transervmedical.domain.model.Calendar
import com.example.transervmedical.domain.use_case.calendar.CalendarEvent
import com.example.transervmedical.domain.use_case.calendar.CalendarUseCases
import com.example.transervmedical.domain.use_case.form.validation.ValidationEvent
import com.example.transervmedical.presentation.states.CalendarState
import com.example.transervmedical.util.Util.provideCalendarId
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val database: FirebaseDatabase,
    private val calendarUseCases: CalendarUseCases,
) : ViewModel() {

    var uiState by mutableStateOf(UiState())
        private set
    var calendarState by mutableStateOf(CalendarState())
    private val userId = firebaseAuth.currentUser?.uid
    private val databaseRef = database.getReference("calendarEvents")
    var calendarEventError by mutableStateOf("No network error")
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        getDatabaseData()
    }

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
                getAllCalendarEventsFromDb()
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
                startEvent = System.currentTimeMillis(),
                endEvent = System.currentTimeMillis(),
                allDay = calendarState.allDay,
                description = calendarState.description
            )
        }
        databaseRef.child("$userId-$calendarId").setValue(calendarObject)
            .addOnCompleteListener {
                viewModelScope.launch {
                    validationEventChannel.send(ValidationEvent.Success)
                    calendarUseCases.addCalendarEvent.invoke(calendarObject)
                }
                Log.d("Firebase", "Complete Message: ${it.exception}")
            }.addOnFailureListener {
                viewModelScope.launch {
                calendarEventError = it.message.toString()
                    validationEventChannel.send(ValidationEvent.Failure)
                }
                Log.d("Firebase", "Failure Message: ${it.message}")
            }
    }


    private fun getDatabaseData() {
        // snapshot data receive
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    val calendar = data.getValue(Calendar::class.java)
                    Log.d("asdf", "calendar : $calendar")
                    if (calendar != null) {
                        viewModelScope.launch {
                            calendarUseCases.addCalendarEvent(calendar)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                calendarEventError = error.message
                viewModelScope.launch {
                    validationEventChannel.send(ValidationEvent.Failure)
                }
                Log.d("Firebase", "Error: ${error.message}")
            }
        })
    }


    fun getAllCalendarEventsFromDb() = viewModelScope.launch {
        val events = calendarUseCases.getAllCalendarEvents.invoke()
        uiState = uiState.copy(
            dashboardEvents = events
        )
    }

    fun getUserEmail(): String? {
        return if (firebaseAuth.currentUser != null)
            firebaseAuth.currentUser!!.email
        else
            "No user is logged in"
    }

    data class UiState(
        val dashboardEvents: Map<String, List<Calendar>> = emptyMap(),
    )
}