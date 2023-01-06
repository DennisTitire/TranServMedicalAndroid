package com.example.transervmedical.navigation

sealed class Screen(val route: String) {
    object Splash : Screen(route = "SplashScreen")
    object LogIn : Screen(route = "LogInScreen")
    object SignIn : Screen(route = "SignInScreen")
    object Dashboard : Screen(route = "HomeScreen")
    object Calendar : Screen(route = "CalendarScreen")
    object AddEvent : Screen(route = "AddEventScreen")
//    object AddEvent : Screen(route = "AddEventScreen/{calendarId}") {
//        fun passCalendarId(calendarId: String): String {
//            return "AddEventScreen/$calendarId"
//        }
//    }
    object Settings : Screen(route = "SettingsScreen")

}