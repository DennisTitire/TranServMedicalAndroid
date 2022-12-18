package com.example.transervmedical.presentation.screens.calendar

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.transervmedical.domain.model.Calendar
import com.example.transervmedical.navigation.Screen
import com.example.transervmedical.presentation.viewmodel.CalendarViewModel
import com.example.transervmedical.ui.theme.Blue
import com.example.transervmedical.util.Util.formatTime
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalFoundationApi
@Composable
fun CalendarScreen(
    navHostController: NavHostController,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
) {
    val state = calendarViewModel.uiState
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
            calendarViewModel.getAllCalendarEventsFromDb()
            state.dashboardEvents.forEach { (day, events) ->
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = day.substring(0, day.indexOf(",")),
                            style = MaterialTheme.typography.h5
                        )
                        Log.d("Dash", "day event = $day")
                        events.forEach { event ->
                            CalendarEventItem(
                                event = event
                            )
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun LazyItemScope.CalendarEventItem(
    event: Calendar,
    //onClick: (Calendar) -> Unit,
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
                    .clickable { /* TODO: Click on calendar item */ }
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
                    text = formatEventStartEnd(
                        start = event.startEvent ?: 0,
                        end = event.endEvent ?: 0,
                        allDay = event.allDay
                    ),
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = event.description ?: "",
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }
}

fun formatEventStartEnd(start: Long, end: Long, allDay: Boolean): String {
    return if (allDay)
        "All day"
    else
        start.formatTime() + " - " + end.formatTime()

}