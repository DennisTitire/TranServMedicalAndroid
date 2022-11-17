package com.example.transervmedical.presentation.screens.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.transervmedical.navigation.Screen
import com.example.transervmedical.ui.theme.Blue
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalFoundationApi
@Composable
fun CalendarScreen(
    navHostController: NavHostController
) {
    val lazyListState = rememberLazyListState()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Calendar", fontSize = 32.sp) },
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
                }
            )
        }
    ) {
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            calendarListEvents.forEach { event ->
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = event.day,
                            style = MaterialTheme.typography.h5,
                            color = if (isSystemInDarkTheme()) Color.White else Color.Black
                        )
                        CalendarEventItem(event = event)
                    }
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun LazyItemScope.CalendarEventItem(
    event: CalendarEvent
) {
    Card(
        modifier = Modifier
            .animateItemPlacement(),
        shape = RoundedCornerShape(20.dp),
        elevation = 8.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(8.dp)
                    .height(34.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Blue)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column(
                modifier = Modifier
                    .clickable {  /* TODO */ }
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (event.allDay) "All day" else "from ${event.start} to ${event.end}",
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }
}


data class CalendarEvent(
    val id: Long,
    val title: String,
    val description: String?,
    val start: Long,
    val end: Long,
    val location: String?,
    val allDay: Boolean = false,
    val color: Int = 0xFF2965,
    val recurring: Boolean = false,
    val frequency: String = "",
    val day: String
)

// CalendarUtil
//fun formatEventStartEnd(start: Long, end: Long, location: String?, allDay: Boolean): String {
//    return if (allDay)
//        "All day"
//    else
//            if (!location.isNullOrBlank())
//                    "%1$s - %2$s at %3\$"
//            else "%1$s - %2$s",
//            start.formatTime(),
//            end.formatTime(),
//            location ?: ""
//
//}

fun Long.formatTime(): String {
    val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
    val sdfNoMinutes = SimpleDateFormat("h a", Locale.getDefault())
    val minutes = SimpleDateFormat("mm", Locale.getDefault()).format(this)
    return if (minutes == "00") sdfNoMinutes.format(this) else sdf.format(this)
}

//fun getString(
//    @StringRes
//    resId: Int,
//    vararg args: String
//) = MainActivity.appContext.getString(resId, *args)

val calendarListEvents = listOf(
    CalendarEvent(
        id = 0,
        title = "Shopping",
        description = "asdf",
        start = 12312,
        end = 123142,
        location = null,
        allDay = true,
        color = 0xFF2965,
        recurring = true,
        frequency = "",
        day = "Monday"
    ),
    CalendarEvent(
        id = 1,
        title = "Gym",
        description = "",
        start = 123422,
        end = 12324312,
        location = null,
        allDay = true,
        color = 0xFFFF98,
        recurring = true,
        frequency = "",
        day = "Tuesday"
    ),
    CalendarEvent(
        id = 2,
        title = "Lunch",
        description = "",
        start = 768,
        end = 456092,
        location = null,
        allDay = true,
        color = 0xFF1E96,
        recurring = true,
        frequency = "",
        day = "Wednesday"
    ),
    CalendarEvent(
        id = 2,
        title = "Lunch",
        description = "",
        start = 768,
        end = 456092,
        location = null,
        allDay = true,
        color = 0xFF1E96,
        recurring = true,
        frequency = "",
        day = "Wednesday"
    ),
    CalendarEvent(
        id = 2,
        title = "Lunch",
        description = "",
        start = 768,
        end = 456092,
        location = null,
        allDay = true,
        color = 0xFF1E96,
        recurring = true,
        frequency = "",
        day = "Wednesday"
    ),
    CalendarEvent(
        id = 2,
        title = "Lunch",
        description = "",
        start = 768,
        end = 456092,
        location = null,
        allDay = true,
        color = 0xFF1E96,
        recurring = true,
        frequency = "",
        day = "Wednesday"
    ),
    CalendarEvent(
        id = 2,
        title = "Lunch",
        description = "",
        start = 768,
        end = 456092,
        location = null,
        allDay = true,
        color = 0xFF1E96,
        recurring = true,
        frequency = "",
        day = "Wednesday"
    )
)