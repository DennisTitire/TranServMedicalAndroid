package com.example.transervmedical.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.transervmedical.presentation.screens.add_event.AddEventScreen
import com.example.transervmedical.presentation.screens.calendar.CalendarScreen
import com.example.transervmedical.presentation.screens.dashboard.DashboardScreen
import com.example.transervmedical.presentation.screens.forgotPassword.ForgotPasswordScreen
import com.example.transervmedical.presentation.screens.log_in.LogInScreen
import com.example.transervmedical.presentation.screens.settings.SettingsScreen
import com.example.transervmedical.presentation.screens.sign_in.SignInScreen
import com.example.transervmedical.presentation.screens.splash.SplashScreen
import com.example.transervmedical.util.Util.CALENDAR_EVENT_ARG
import com.google.firebase.auth.FirebaseAuth

@ExperimentalFoundationApi
@Composable
fun Routes(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navHostController = navHostController)
        }
        composable(route = Screen.Dashboard.route) {
            DashboardScreen(navHostController = navHostController)
        }
        composable(route = Screen.Calendar.route) {
            CalendarScreen(navHostController = navHostController)
        }
        composable(
            route = Screen.AddEvent.route + "?calendarId={calendarId}",
            arguments = listOf(
                navArgument(
                    name = CALENDAR_EVENT_ARG
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                })
        ) {
            AddEventScreen(
                navHostController = navHostController,
            )
        }
        composable(route = Screen.LogIn.route) {
            LogInScreen(navHostController = navHostController)
        }
        composable(route = Screen.SignIn.route) {
            SignInScreen(navHostController = navHostController)
        }
        composable(route = Screen.Settings.route) {
            SettingsScreen(navHostController = navHostController)
        }
        composable(route = Screen.ForgotPassword.route) {
            ForgotPasswordScreen(navHostController = navHostController)
        }
    }

}