package com.example.transervmedical.presentation.screens.forgotPassword

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.transervmedical.R
import com.example.transervmedical.domain.use_case.form.login.LogInFormEvent
import com.example.transervmedical.domain.use_case.form.validation.ValidationEvent
import com.example.transervmedical.navigation.Screen
import com.example.transervmedical.presentation.screens.components.ReusableComponents
import com.example.transervmedical.presentation.viewmodel.UserViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ForgotPasswordScreen(
    navHostController: NavHostController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    val loginState = userViewModel.loginState
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = true) {
        userViewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navHostController.popBackStack()
                }
                is ValidationEvent.Failure -> {
                    /* TODO: Making a Failure for LoadingState */
                }
                is ValidationEvent.Loading -> {
                    /* TODO: Making a loading for LoadingState */
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.ForgotPassword), fontSize = 32.sp) },
                backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
                contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.navigate(Screen.LogIn.route)

                    }) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.BackButton)
                        )
                    }
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            ReusableComponents.EditTextEmailOutline(
                value = loginState.email,
                onValueChange = { userViewModel.onEventLogin(LogInFormEvent.ForgotPassword(it)) },
                label = stringResource(R.string.Email),
                isError = loginState.emailErrorType,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                })
            )
            if (loginState.emailError != null) {
                Text(
                    text = loginState.emailError,
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.error
                )
            }
            ReusableComponents.BlueButton(
                onClick = {
                    userViewModel.onEventLogin(LogInFormEvent.ForgotPasswordSubmit)
                },
                buttonText = stringResource(R.string.SendEmail),
            )
        }
    }
}