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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.transervmedical.R
import com.example.transervmedical.navigation.Screen
import com.example.transervmedical.ui.theme.Blue
import com.example.transervmedical.ui.theme.Orange
import com.example.transervmedical.ui.theme.Red

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    navHostController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "Dashboard",
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
                title = "Calendar",
                image = R.drawable.calendar_img,
                backgroundColor = Red
            ) {
                navHostController.navigate(Screen.Calendar.route)
            }
            Spacer(modifier = Modifier.height(16.dp))
            HomeItem(
                title = "Add Event",
                image = R.drawable.calendar_add_event_image,
                backgroundColor = Blue,
            ) {
                navHostController.navigate(Screen.AddEvent.route)
            }
            Spacer(modifier = Modifier.height(16.dp))
            HomeItem(
                title = "Settings",
                image = R.drawable.calendar_add_event_image,
                backgroundColor = Orange,
            ) {
                navHostController.navigate(Screen.Settings.route)
            }
        }
    }
}