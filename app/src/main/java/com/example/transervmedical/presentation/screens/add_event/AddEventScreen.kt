package com.example.transervmedical.presentation.screens.add_event

import android.app.DatePickerDialog
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
import androidx.navigation.NavHostController
import com.example.transervmedical.R
import com.example.transervmedical.navigation.Screen
import com.example.transervmedical.presentation.screens.components.ReusableComponents.BlueButton
import com.example.transervmedical.presentation.screens.components.ReusableComponents.EditTextEmailOutline
import com.example.transervmedical.ui.theme.Blue

@Composable
fun AddEventScreen(
    navHostController: NavHostController
) {
    val context = LocalContext.current
    var titleEvent by remember { mutableStateOf("") }
    var descriptionEvent by remember { mutableStateOf("") }
    var allDay by rememberSaveable { mutableStateOf(true) }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Calendar Event", fontSize = 32.sp) },
                backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
                contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.navigate(Screen.LogIn.route)
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            EditTextEmailOutline(
                value = titleEvent,
                onValueChange = { titleEvent = it },
                label = "Title",
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
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
                        checked = allDay,
                        onCheckedChange = { allDay = !allDay },
                        colors = SwitchDefaults.colors(Blue)
                    )
                }
                AnimatedVisibility(visible = !allDay) {
                    Column {
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
                                    .clickable { DatePickerDialog(context).show() }
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
                    }
                }
                EditTextEmailOutline(
                    value = descriptionEvent,
                    onValueChange = { descriptionEvent = it },
                    label = "Description",
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_description),
                            contentDescription = "description"
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done)
                )
                Spacer(modifier = Modifier.height(12.dp))
                BlueButton(
                    onClick = { /* TODO */ },
                    buttonText = "Save Event"
                )
            }
        }
    }
}
