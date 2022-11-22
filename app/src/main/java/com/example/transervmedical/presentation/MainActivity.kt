package com.example.transervmedical.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Surface
import androidx.navigation.compose.rememberNavController
import com.example.transervmedical.navigation.Routes
import com.example.transervmedical.ui.theme.TranServMedicalTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    private val firebaseAuth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TranServMedicalTheme {
                Surface {
                    val navHostController = rememberNavController()
                    Routes(navHostController = navHostController)
                }
            }
        }
    }

    override fun onStart() {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            
        }
        super.onStart()
    }
}