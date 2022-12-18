package com.example.transervmedical.presentation.screens.add_event

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.transervmedical.navigation.Screen
import com.example.transervmedical.presentation.screens.components.ReusableComponents.BlueButton
import com.example.transervmedical.presentation.screens.components.ReusableComponents.EditTextEmailOutline
import com.example.transervmedical.presentation.viewmodel.CalendarViewModel
import com.example.transervmedical.ui.theme.Blue
import com.example.transervmedical.util.Util.HOUR_IN_MILLIS
import com.example.transervmedical.util.Util.formatDate
import com.example.transervmedical.util.Util.formatTime
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEventScreen(
    navHostController: NavHostController,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
) {
    val focusManager = LocalFocusManager.current
    val calendarState = calendarViewModel.calendarState

    var startDate by rememberSaveable {
        mutableStateOf(
            calendarState.startEvent
        )
    }
    var endDate by rememberSaveable {
        mutableStateOf(
            calendarState.endEvent
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Calendar Event", fontSize = 32.sp) },
                backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
                contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.navigate(Screen.Dashboard.route)
                    }) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back button"
                        )
                    }
                },
//                actions = {
//                    if (calendarState.calendarId != null) {
//                        IconButton(onClick = { /*TODO*/ }) {
//                            Icon(
//                                imageVector = Icons.Default.Delete,
//                                contentDescription = "Delete button")
//                        }
//                    }
//                }
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
                onValueChange = { calendarViewModel.onCalendarEvent(CalendarEvent.TitleChanged(it)) },
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
                        start = Calendar.getInstance().apply {
                            timeInMillis = startDate
                        },
                        end = Calendar.getInstance().apply {
                            timeInMillis = endDate
                        },
                        onStartDateSelected = {
                            startDate = it.timeInMillis
                            calendarState.startEvent = startDate
                            calendarViewModel.onCalendarEvent(CalendarEvent.StartEvent(calendarState.startEvent))
                        },
                        onEndDateSelected = {
                            endDate = it.timeInMillis
                            calendarState.endEvent = endDate
                            calendarViewModel.onCalendarEvent(CalendarEvent.EndEvent(calendarState.endEvent))
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
                        calendarViewModel.onCalendarEvent(CalendarEvent.AddCalendarEvent)
                        navHostController.navigate(route = Screen.Dashboard.route)
                    },
                    buttonText = "Save Event"
                )
            }
        }
    }
}

@Composable
fun EventTimeSection(
    start: Calendar,
    end: Calendar,
    onStartDateSelected: (Calendar) -> Unit,
    onEndDateSelected: (Calendar) -> Unit,
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
                            val newEvent = Calendar
                                .getInstance()
                                .apply {
                                    this[Calendar.YEAR] = it[Calendar.YEAR]
                                    this[Calendar.MONTH] = it[Calendar.MONTH]
                                    this[Calendar.DAY_OF_MONTH] = it[Calendar.DAY_OF_MONTH]

                                    this[Calendar.HOUR_OF_DAY] = start[Calendar.HOUR_OF_DAY]
                                    this[Calendar.MINUTE] = start[Calendar.MINUTE]
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
                            val newEvent = Calendar
                                .getInstance()
                                .apply {
                                    this[Calendar.HOUR_OF_DAY] = it[Calendar.HOUR_OF_DAY]
                                    this[Calendar.MINUTE] = it[Calendar.MINUTE]

                                    this[Calendar.YEAR] = start[Calendar.YEAR]
                                    this[Calendar.MONTH] = start[Calendar.MONTH]
                                    this[Calendar.DAY_OF_MONTH] = start[Calendar.DAY_OF_MONTH]
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
                            val newEvent = Calendar
                                .getInstance()
                                .apply {
                                    this[Calendar.YEAR] = it[Calendar.YEAR]
                                    this[Calendar.MONTH] = it[Calendar.MONTH]
                                    this[Calendar.DAY_OF_MONTH] = it[Calendar.DAY_OF_MONTH]

                                    this[Calendar.HOUR_OF_DAY] = end[Calendar.HOUR_OF_DAY]
                                    this[Calendar.MINUTE] = end[Calendar.MINUTE]
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
                            val newEvent = Calendar
                                .getInstance()
                                .apply {
                                    this[Calendar.HOUR_OF_DAY] = it[Calendar.HOUR_OF_DAY]
                                    this[Calendar.MINUTE] = it[Calendar.MINUTE]

                                    this[Calendar.YEAR] = end[Calendar.YEAR]
                                    this[Calendar.MONTH] = end[Calendar.MONTH]
                                    this[Calendar.DAY_OF_MONTH] = end[Calendar.DAY_OF_MONTH]
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
    initialDate: Calendar,
    context: Context,
    onDateSelected: (Calendar) -> Unit,
) {
    val tempDate = Calendar.getInstance()
    val datePicker = DatePickerDialog(
        context,
        { _, year, month, day ->
            tempDate[Calendar.YEAR] = year
            tempDate[Calendar.MONTH] = month
            tempDate[Calendar.DAY_OF_MONTH] = day
            onDateSelected(tempDate)
        },
        initialDate[Calendar.YEAR],
        initialDate[Calendar.MONTH],
        initialDate[Calendar.DAY_OF_MONTH]
    )
    datePicker.show()
}

fun showTimePicker(
    initialDate: Calendar,
    context: Context,
    onTimeSelected: (Calendar) -> Unit,
) {
    val tempDate = Calendar.getInstance()
    val timePicker = TimePickerDialog(
        context,
        { _, hour, minute ->
            tempDate[Calendar.HOUR_OF_DAY] = hour
            tempDate[Calendar.MINUTE] = minute
            onTimeSelected(tempDate)
        },
        initialDate[Calendar.HOUR_OF_DAY],
        initialDate[Calendar.MINUTE],
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