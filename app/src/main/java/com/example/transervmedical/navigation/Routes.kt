package com.example.transervmedical.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.transervmedical.presentation.screens.add_event.AddEventScreen
import com.example.transervmedical.presentation.screens.calendar.CalendarScreen
import com.example.transervmedical.presentation.screens.home.HomeScreen
import com.example.transervmedical.presentation.screens.log_in.LogInScreen
import com.example.transervmedical.presentation.screens.sign_in.SignInScreen
import com.example.transervmedical.presentation.screens.splash.SplashScreen

@ExperimentalFoundationApi
@Composable
fun Routes(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navHostController = navHostController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navHostController = navHostController)
        }
        composable(route = Screen.Calendar.route) {
            CalendarScreen()
        }
        composable(route = Screen.AddEvent.route) {
            AddEventScreen(navHostController = navHostController)
        }
        composable(route = Screen.LogIn.route) {
            LogInScreen(navHostController = navHostController)
        }
        composable(route = Screen.SignIn.route) {
            SignInScreen(navHostController = navHostController)
        }
    }

}