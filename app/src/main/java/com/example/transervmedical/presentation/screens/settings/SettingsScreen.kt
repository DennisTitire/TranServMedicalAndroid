package com.example.transervmedical.presentation.screens.settings

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.transervmedical.R
import com.example.transervmedical.domain.use_case.form.login.LogInFormEvent
import com.example.transervmedical.navigation.Screen
import com.example.transervmedical.presentation.screens.components.ReusableComponents.BlueButton
import com.example.transervmedical.presentation.viewmodel.UserViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    navHostController: NavHostController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    val nightMode by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.Settings), fontSize = 32.sp) },
                backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
                contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.navigate(Screen.Dashboard.route)
                    }) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.BackButton)
                        )
                    }
                }
            )
        }
    ) {
        LaunchedEffect(Unit) {
            userViewModel.toastMessage
                .collect { message ->
                    Toast.makeText(
                        context,
                        message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            /*  NighMode in Settings
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (nightMode) Icons.Default.ModeNight else Icons.Default.LightMode,
                        null
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        modifier = Modifier.padding(start = 24.dp),
                        text = "Night Mode",
                        style = MaterialTheme.typography.body1
                    )
                }
                Switch(
                    checked = nightMode,
                    onCheckedChange = { nightMode = !nightMode }
                )
            }
             */
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        userViewModel.changePasswordWithEmail()
                    }
            ) {
                Icon(
                    painter = if (!nightMode) painterResource(id = R.drawable.icon_password_reset_dark)
                    else painterResource(id = R.drawable.icon_password_reset_light),
                    contentDescription = stringResource(R.string.IconResetPassword)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = stringResource(R.string.ClickHereToChangeThePassword),
                    style = MaterialTheme.typography.body1
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            BlueButton(
                onClick = {
                    Firebase.auth.signOut()
                    userViewModel.onEventLogin(LogInFormEvent.ForgotPasswordSubmit)
                    navHostController.popBackStack()
                    navHostController.navigate(route = Screen.LogIn.route)
                },
                buttonText = stringResource(R.string.SignOut)
            )
        }
    }
}