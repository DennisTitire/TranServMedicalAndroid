package com.example.transervmedical.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Surface
import androidx.lifecycle.Lifecycle
import androidx.navigation.compose.rememberNavController
import com.example.transervmedical.navigation.Routes
import com.example.transervmedical.navigation.Screen
import com.example.transervmedical.presentation.viewmodel.UserViewModel
import com.example.transervmedical.ui.theme.TranServMedicalTheme
import com.example.transervmedical.util.LifecycleEvent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    private val firebaseAuth = Firebase.auth
    private val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            TranServMedicalTheme {
                LifecycleEvent { lifeCycleEvent ->
                    when (lifeCycleEvent) {
                        Lifecycle.Event.ON_START -> {
                            if (firebaseAuth.currentUser != null) {
                                navHostController.popBackStack()
                                navHostController.navigate(Screen.Dashboard.route)
                            } else {
                                navHostController.navigate(Screen.LogIn.route)
                            }
                        }
                        else -> {}
                    }
                }
                Surface {
                    Routes(navHostController = navHostController)
                }
            }
        }
    }
}