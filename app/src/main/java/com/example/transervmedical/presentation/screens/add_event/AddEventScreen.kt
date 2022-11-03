package com.example.transervmedical.presentation.screens.add_event

import android.app.DatePickerDialog
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.transervmedical.R

@Composable
fun AddEventScreen(
    navHostController: NavHostController
) {
    val context = LocalContext.current
    var titleEvent by remember { mutableStateOf(TextFieldValue("")) }
    var descriptionEvent by remember { mutableStateOf(TextFieldValue("")) }
    var allDay by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(start = 12.dp, end = 12.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            modifier = Modifier.padding(start = 24.dp),
            text = "Add Event",
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
        )
        OutlinedTextField(
            value = titleEvent,
            onValueChange = { titleEvent = it },
            label = { Text(text = "Title") },
            shape = RoundedCornerShape(15.dp),
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
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
                    checked = allDay,
                    onCheckedChange = { allDay = !allDay }
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Start: Thu, Nov 03, 2022",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .clickable { DatePickerDialog(context).show() }
                        .padding(horizontal = 28.dp, vertical = 16.dp)
                )
                Text(
                    text = "2:02 PM",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .clickable { "TimePickerDialog(context).show()" }
                        .padding(horizontal = 18.dp, vertical = 16.dp)

                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "End: Fri, Nov 03, 2022",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .clickable { /* TODO */ }
                        .padding(horizontal = 28.dp, vertical = 16.dp)
                )
                Text(
                    text = "2:02 PM",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .clickable { /* TODO */ }
                        .padding(horizontal = 18.dp, vertical = 16.dp)
                )
            }

            OutlinedTextField(
                value = descriptionEvent,
                onValueChange = { descriptionEvent = it },
                label = { Text(text = "Description") },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.ic_description), null)
                }
            )
            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
            ) {
                Text(
                    text = "Save Event",
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.SemiBold),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
