package com.example.transervmedical.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Surface
import androidx.navigation.compose.rememberNavController
import com.example.transervmedical.navigation.Routes
import com.example.transervmedical.presentation.viewmodel.UserViewModel
import com.example.transervmedical.ui.theme.TranServMedicalTheme
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
            TranServMedicalTheme {
                Surface {
                    val navHostController = rememberNavController()
                    Routes(navHostController = navHostController)
                }
            }
        }
    }

//    override fun onStart() {
//        setContent {
//            val currentUser = firebaseAuth.currentUser
//            if (currentUser != null && userViewModel.rememberMe == true) {
//                this.startActivity(Actions.openDashboardIntent(this)
//                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_CLEAR_TOP))
//            } else {
//                this.startActivity(Actions.openLogInIntent(this)
//                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_CLEAR_TOP))
//            }
//        }
//        super.onStart()
//    }
}