package com.example.transervmedical.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.transervmedical.presentation.screens.home.HomeScreen
import com.example.transervmedical.presentation.screens.splash.SplashScreen

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
    }

}