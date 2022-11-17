package com.example.transervmedical.navigation

sealed class Screen(val route: String) {
    object Splash : Screen(route = "SplashScreen")
    object LogIn : Screen(route = "LogInScreen")
    object SignIn : Screen(route = "SignInScreen")
    object Dashboard : Screen(route = "HomeScreen")
    object Calendar : Screen(route = "CalendarScreen")
    object AddEvent : Screen(route = "AddEventScreen")
    object Settings : Screen(route = "SettingsScreen")

}