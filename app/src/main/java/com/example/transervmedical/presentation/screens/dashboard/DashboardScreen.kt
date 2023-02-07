package com.example.transervmedical.presentation.screens.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.transervmedical.R
import com.example.transervmedical.navigation.Screen
import com.example.transervmedical.ui.theme.Blue
import com.example.transervmedical.ui.theme.Orange
import com.example.transervmedical.ui.theme.Red
import com.example.transervmedical.util.BackButtonPressHandler

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    navHostController: NavHostController,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(R.string.Dashboard),
                        fontSize = 32.sp
                    )
                },
                backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
                contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            HomeItem(
                title = stringResource(id = R.string.Calendar),
                image = R.drawable.icon_calendar_events,
                backgroundColor = Red
            ) {
                navHostController.navigate(Screen.Calendar.route)
            }
            Spacer(modifier = Modifier.height(16.dp))
            HomeItem(
                title = stringResource(id = R.string.AddEvent),
                image = R.drawable.icon_add_calendar_event,
                backgroundColor = Blue,
            ) {
                navHostController.navigate(Screen.AddEvent.route)
            }
            Spacer(modifier = Modifier.height(16.dp))
            HomeItem(
                title = stringResource(R.string.Settings),
                image = R.drawable.icon_settings_calendar,
                backgroundColor = Orange,
            ) {
                navHostController.navigate(Screen.Settings.route)
            }
        }
    }
}