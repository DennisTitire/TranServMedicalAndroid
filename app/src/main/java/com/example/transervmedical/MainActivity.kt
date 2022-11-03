package com.example.transervmedical

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.compose.rememberNavController
import com.example.transervmedical.navigation.Routes
import com.example.transervmedical.ui.theme.TranServMedicalTheme

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TranServMedicalTheme {
                val navHostController = rememberNavController()
                Routes(navHostController = navHostController)
            }
        }
    }
}