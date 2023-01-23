package com.example.transervmedical.presentation.screens.add_event

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.transervmedical.R
import com.example.transervmedical.domain.use_case.calendar.CalendarEvent
import com.example.transervmedical.domain.use_case.form.validation.ValidationEvent
import com.example.transervmedical.navigation.Screen
import com.example.transervmedical.presentation.screens.components.ReusableComponents.BlueButton
import com.example.transervmedical.presentation.screens.components.ReusableComponents.EditTextEmailOutline
import com.example.transervmedical.presentation.viewmodel.CalendarViewModel
import com.example.transervmedical.ui.theme.Blue
import com.example.transervmedical.util.Util.HOUR_IN_MILLIS
import com.example.transervmedical.util.Util.formatDate
import com.example.transervmedical.util.Util.formatTime

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEventScreen(
    navHostController: NavHostController,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scaffoldState = rememberScaffoldState()
    val calendarState = calendarViewModel.calendarState
    val calendarEvent = calendarViewModel.selectedCalendarEvent.collectAsState().value

    LaunchedEffect(key1 = true) {
        calendarViewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navHostController.navigate(route = Screen.Dashboard.route)
                }
                is ValidationEvent.Failure -> {
                    Toast.makeText(
                        context,
                        calendarViewModel.calendarEventError,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is ValidationEvent.Loading -> {
                    // TODO: Need to implement
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Calendar Event", fontSize = 32.sp) },
                backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
                contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                navigationIcon = {
                    IconButton(onClick = {
                        if (calendarEvent != null) {
                            navHostController.navigate(Screen.Calendar.route)
                        } else {
                            navHostController.navigate(Screen.Dashboard.route)
                        }
                    }) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back button"
                        )
                    }
                },
                actions = {
                    if (calendarEvent?.calendarId != null) {
                        IconButton(onClick = {
                            calendarViewModel.onCalendarEvent(CalendarEvent.DeleteCalendarEvent(calendarEvent))
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete button")
                        }
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            EditTextEmailOutline(
                value = calendarState.title,
                onValueChange = { calendarViewModel.onCalendarEvent(CalendarEvent.TitleChanged(title = it)) },
                label = "Title",
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                })
            )
            Spacer(Modifier.height(8.dp))
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painter = painterResource(R.drawable.ic_time), null)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "All Day",
                            style = MaterialTheme.typography.body1
                        )
                    }
                    Switch(
                        checked = calendarState.allDay,
                        onCheckedChange = {
                            calendarViewModel.onCalendarEvent(
                                CalendarEvent.AllDay(
                                    it
                                )
                            )
                        },
                        colors = SwitchDefaults.colors(Blue)
                    )
                }
                AnimatedVisibility(visible = !calendarState.allDay) {
                    EventTimeSection(
                        start = java.util.Calendar.getInstance().apply {
                            timeInMillis = calendarEvent?.startEvent ?: calendarState.startEvent
                        },
                        end = java.util.Calendar.getInstance().apply {
                            timeInMillis = calendarEvent?.endEvent ?: calendarState.endEvent
                        },
                        onStartDateSelected = {
                            if (calendarEvent != null) {
                                calendarEvent.startEvent = it.timeInMillis
                                calendarViewModel.onCalendarEvent(CalendarEvent.StartEvent(
                                    startEvent = calendarEvent.startEvent))
                            } else {
                                calendarViewModel.onCalendarEvent(CalendarEvent.StartEvent(
                                    startEvent = it.timeInMillis))
                            }
                        },
                        onEndDateSelected = {
                            if (calendarEvent != null) {
                                calendarEvent.endEvent = it.timeInMillis
                                calendarViewModel.onCalendarEvent(CalendarEvent.EndEvent(
                                    endEvent = calendarEvent.endEvent))
                            } else {
                                calendarViewModel.onCalendarEvent(CalendarEvent.EndEvent(
                                    endEvent = it.timeInMillis))
                            }
                        },
                    )
                }
                EditTextEmailOutline(
                    value = calendarState.description ?: "",
                    onValueChange = {
                        calendarViewModel.onCalendarEvent(
                            CalendarEvent.DescriptionChanged(
                                it
                            )
                        )
                    },
                    label = "Description",
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_description),
                            contentDescription = "description"
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                BlueButton(
                    onClick = {
                        if (calendarEvent != null) {
                            calendarViewModel.onCalendarEvent(CalendarEvent.UpdateCalendarEvent(calendarEvent.copy(
                                title = calendarState.title,
                                allDay = calendarState.allDay,
                                startEvent = calendarState.startEvent,
                                endEvent = calendarState.endEvent,
                                description = calendarState.description
                            )))
                        } else {
                            calendarViewModel.onCalendarEvent(CalendarEvent.AddCalendarEvent)
                        }
                    },
                    buttonText = if (calendarEvent != null) "Update Event" else "Add Event"
                )
            }
        }
    }
}

@Composable
fun EventTimeSection(
    start: java.util.Calendar,
    end: java.util.Calendar,
    onStartDateSelected: (java.util.Calendar) -> Unit,
    onEndDateSelected: (java.util.Calendar) -> Unit,
) {
    val context = LocalContext.current
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = start.timeInMillis.formatDate(),
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .clickable {
                        showDatePicker(start, context) {
                            val newEvent = java.util.Calendar
                                .getInstance()
                                .apply {
                                    this[java.util.Calendar.YEAR] = it[java.util.Calendar.YEAR]
                                    this[java.util.Calendar.MONTH] = it[java.util.Calendar.MONTH]
                                    this[java.util.Calendar.DAY_OF_MONTH] =
                                        it[java.util.Calendar.DAY_OF_MONTH]

                                    this[java.util.Calendar.HOUR_OF_DAY] =
                                        start[java.util.Calendar.HOUR_OF_DAY]
                                    this[java.util.Calendar.MINUTE] =
                                        start[java.util.Calendar.MINUTE]
                                }
                            onStartDateSelected(
                                newEvent
                            )
                            if (newEvent.timeInMillis > end.timeInMillis) {
                                onEndDateSelected(newEvent.apply { timeInMillis += HOUR_IN_MILLIS })
                            }
                        }
                    }
                    .padding(horizontal = 28.dp, vertical = 16.dp)
            )
            Text(
                text = start.timeInMillis.formatTime(),
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .clickable {
                        showTimePicker(start, context) {
                            val newEvent = java.util.Calendar
                                .getInstance()
                                .apply {
                                    this[java.util.Calendar.HOUR_OF_DAY] =
                                        it[java.util.Calendar.HOUR_OF_DAY]
                                    this[java.util.Calendar.MINUTE] = it[java.util.Calendar.MINUTE]

                                    this[java.util.Calendar.YEAR] = start[java.util.Calendar.YEAR]
                                    this[java.util.Calendar.MONTH] = start[java.util.Calendar.MONTH]
                                    this[java.util.Calendar.DAY_OF_MONTH] =
                                        start[java.util.Calendar.DAY_OF_MONTH]
                                }
                            onStartDateSelected(newEvent)
                            if (newEvent.timeInMillis > end.timeInMillis) {
                                onEndDateSelected(newEvent.apply { timeInMillis += HOUR_IN_MILLIS })
                            }
                        }
                    }
                    .padding(horizontal = 18.dp, vertical = 16.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = end.timeInMillis.formatDate(),
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .clickable {
                        showDatePicker(end, context) {
                            val newEvent = java.util.Calendar
                                .getInstance()
                                .apply {
                                    this[java.util.Calendar.YEAR] = it[java.util.Calendar.YEAR]
                                    this[java.util.Calendar.MONTH] = it[java.util.Calendar.MONTH]
                                    this[java.util.Calendar.DAY_OF_MONTH] =
                                        it[java.util.Calendar.DAY_OF_MONTH]

                                    this[java.util.Calendar.HOUR_OF_DAY] =
                                        end[java.util.Calendar.HOUR_OF_DAY]
                                    this[java.util.Calendar.MINUTE] = end[java.util.Calendar.MINUTE]
                                }
                            onEndDateSelected(newEvent)
                            if (newEvent.timeInMillis < start.timeInMillis) {
                                onStartDateSelected(newEvent.apply { timeInMillis -= HOUR_IN_MILLIS })
                            }
                        }
                    }
                    .padding(horizontal = 28.dp, vertical = 16.dp)
            )
            Text(
                text = end.timeInMillis.formatTime(),
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .clickable {
                        showTimePicker(end, context) {
                            val newEvent = java.util.Calendar
                                .getInstance()
                                .apply {
                                    this[java.util.Calendar.HOUR_OF_DAY] =
                                        it[java.util.Calendar.HOUR_OF_DAY]
                                    this[java.util.Calendar.MINUTE] = it[java.util.Calendar.MINUTE]

                                    this[java.util.Calendar.YEAR] = end[java.util.Calendar.YEAR]
                                    this[java.util.Calendar.MONTH] = end[java.util.Calendar.MONTH]
                                    this[java.util.Calendar.DAY_OF_MONTH] =
                                        end[java.util.Calendar.DAY_OF_MONTH]
                                }
                            onEndDateSelected(newEvent)
                            if (newEvent.timeInMillis < start.timeInMillis) {
                                onStartDateSelected(newEvent.apply { timeInMillis -= HOUR_IN_MILLIS })
                            }
                        }
                    }
                    .padding(horizontal = 18.dp, vertical = 16.dp)
            )
        }
    }
}

fun showDatePicker(
    initialDate: java.util.Calendar,
    context: Context,
    onDateSelected: (java.util.Calendar) -> Unit,
) {
    val tempDate = java.util.Calendar.getInstance()
    val datePicker = DatePickerDialog(
        context,
        { _, year, month, day ->
            tempDate[java.util.Calendar.YEAR] = year
            tempDate[java.util.Calendar.MONTH] = month
            tempDate[java.util.Calendar.DAY_OF_MONTH] = day
            onDateSelected(tempDate)
        },
        initialDate[java.util.Calendar.YEAR],
        initialDate[java.util.Calendar.MONTH],
        initialDate[java.util.Calendar.DAY_OF_MONTH]
    )
    datePicker.show()
}

fun showTimePicker(
    initialDate: java.util.Calendar,
    context: Context,
    onTimeSelected: (java.util.Calendar) -> Unit,
) {
    val tempDate = java.util.Calendar.getInstance()
    val timePicker = TimePickerDialog(
        context,
        { _, hour, minute ->
            tempDate[java.util.Calendar.HOUR_OF_DAY] = hour
            tempDate[java.util.Calendar.MINUTE] = minute
            onTimeSelected(tempDate)
        },
        initialDate[java.util.Calendar.HOUR_OF_DAY],
        initialDate[java.util.Calendar.MINUTE],
        false
    )
    timePicker.show()
}

/* Dialog for date and time
// start date dialog
MaterialDialog(
    dialogState = startDateDialogState,
    buttons = {
        positiveButton(text = "Ok")
        negativeButton(text = "Cancel")
    }
) {
    datepicker(
        initialDate = LocalDate.now(),
        title = "Pick a date",
    ) {
        calendarViewModel.onCalendarEvent(CalendarEvent.StartEventDay(it))
    }
}

// start time dialog
MaterialDialog(
    dialogState = startTimeDialogState,
    buttons = {
        positiveButton(text = "Ok")
        negativeButton(text = "Cancel")
    }
) {
    timepicker(
        initialTime = LocalTime.now(),
        title = "Pick a date",
    ) {
        calendarViewModel.onCalendarEvent(CalendarEvent.StartEventTime(it))
    }
}

// end date dialog
MaterialDialog(
    dialogState = endDateDialogState,
    buttons = {
        positiveButton(text = "Ok")
        negativeButton(text = "Cancel")
    }
) {
    datepicker(
        initialDate = LocalDate.now(),
        title = "Pick a date",
    ) {
        calendarViewModel.onCalendarEvent(CalendarEvent.EndEventDay(it))
    }
}

// end time dialog
MaterialDialog(
    dialogState = endTimeDialogState,
    buttons = {
        positiveButton(text = "Ok")
        negativeButton(text = "Cancel")
    }
) {
    timepicker(
        initialTime = LocalTime.now(),
        title = "Pick a date",
    ) {
        calendarViewModel.onCalendarEvent(CalendarEvent.EndEventTime(it))
    }
}
}
 */

/* Date Piker Dialog
@Composable
fun DatePikerDialog(
    calendarState: CalendarState
) {
    calendarState.startEventDay
    var pickedDate by remember { mutableStateOf(LocalDate.now()) }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDate)
        }
    }

    val dateDialogState = rememberMaterialDialogState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                dateDialogState.show()
            }
        ) {
            Text(text = "Pick date")
        }
        Text(text = formattedDate)
    }

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok")
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a date",

            ) {
            pickedDate = it
        }
    }
}
 */