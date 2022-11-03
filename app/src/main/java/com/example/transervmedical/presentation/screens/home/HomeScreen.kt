package com.example.transervmedical.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.transervmedical.R
import com.example.transervmedical.navigation.Screen
import com.example.transervmedical.ui.theme.Green
import com.example.transervmedical.ui.theme.Red

@Composable
fun HomeScreen(
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier.padding(start = 24.dp),
            text = "Dashboard",
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
        )
        HomeItem(
            title = "Calendar",
            image = R.drawable.calendar_img,
            backgroundColor = Red
        ) {
            navHostController.navigate(Screen.Calendar.route)
        }
        HomeItem(
            title = "Add Event",
            image = R.drawable.calendar_add_event_image,
            backgroundColor = Green,
        ) {
            navHostController.navigate(Screen.AddEvent.route)
        }

        // DELETE FROM HERE
        HomeItem(
            title = "LogIn",
            image = R.drawable.calendar_add_event_image,
            backgroundColor = Green,
        ) {
            navHostController.navigate(Screen.LogIn.route)
        }

        HomeItem(
            title = "SignIn",
            image = R.drawable.calendar_add_event_image,
            backgroundColor = Green,
        ) {
            navHostController.navigate(Screen.SignIn.route)
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navHostController = rememberNavController())
}