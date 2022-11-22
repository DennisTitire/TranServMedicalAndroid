package com.example.transervmedical.presentation.screens.sign_in

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.transervmedical.domain.use_case.form.register.RegistrationFormEvent
import com.example.transervmedical.domain.use_case.form.validation.ValidationEvent
import com.example.transervmedical.navigation.Screen
import com.example.transervmedical.presentation.screens.components.ReusableComponents.BlueButton
import com.example.transervmedical.presentation.screens.components.ReusableComponents.EditTextEmailOutline
import com.example.transervmedical.presentation.screens.components.ReusableComponents.EditTextPasswordOutline
import com.example.transervmedical.presentation.viewmodel.UserViewModel

@Composable
fun SignInScreen(
    navHostController: NavHostController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    val registerState = userViewModel.registerState
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = context) {
        userViewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "Added user with email: ${userViewModel.registerState.email}",
                        Toast.LENGTH_SHORT
                    ).show()
                    navHostController.navigate(route = Screen.LogIn.route)
                }
                is ValidationEvent.Failure -> {
                    Toast.makeText(
                        context,
                        userViewModel.firebaseError,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is ValidationEvent.Loading -> {
                    /* TODO: Making a loading for LoadingState */
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Sign up", fontSize = 32.sp) },
                backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
                contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.navigate(Screen.LogIn.route)
                    }) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back button"
                        )
                    }
                }
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Text(
                modifier = Modifier.padding(top = 24.dp, start = 8.dp),
                text = "Create account", fontSize = 32.sp
            )
            EditTextEmailOutline(
                value = registerState.email,
                onValueChange = {
                    userViewModel.onEventRegister(
                        RegistrationFormEvent.EmailChanged(
                            it
                        )
                    )
                },
                label = "Email",
                isError = registerState.emailErrorType,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                })
            )
            if (registerState.emailError != null) {
                Text(
                    text = registerState.emailError,
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.error
                )
            }

            EditTextEmailOutline(
                value = registerState.phoneNumber,
                onValueChange = {
                    userViewModel.onEventRegister(
                        RegistrationFormEvent.PhoneNumberChanged(
                            it
                        )
                    )
                },
                label = "Phone Number",
                isError = registerState.phoneNumberErrorType,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                })
            )

            if (registerState.phoneNumberError != null) {
                Text(
                    text = registerState.phoneNumberError,
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.error
                )
            }
            EditTextPasswordOutline(
                value = registerState.password,
                onValueChange = {
                    userViewModel.onEventRegister(
                        RegistrationFormEvent.PasswordChanged(
                            it
                        )
                    )
                },
                label = "Password",
                isError = registerState.passwordErrorType,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                })
            )
            if (registerState.passwordError != null) {
                Text(
                    text = registerState.passwordError,
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.error
                )
            }
            EditTextPasswordOutline(
                value = registerState.repeatedPassword,
                onValueChange = {
                    userViewModel.onEventRegister(
                        RegistrationFormEvent.RepeatedPasswordChanged(
                            it
                        )
                    )
                },
                label = "Repeated Password",
                isError = registerState.repeatedPasswordErrorType,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )
            if (registerState.repeatedPasswordError != null) {
                Text(
                    text = registerState.repeatedPasswordError,
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.error
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            BlueButton(
                onClick = {
                    userViewModel.onEventRegister(RegistrationFormEvent.Submit)
                },
                buttonText = "Sign up"
            )
        }
    }
}