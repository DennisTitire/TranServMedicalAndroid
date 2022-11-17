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
import com.example.transervmedical.domain.model.User
import com.example.transervmedical.domain.use_case.form.register.RegistrationFormEvent
import com.example.transervmedical.navigation.Screen
import com.example.transervmedical.presentation.screens.components.ReusableComponents.BlueButton
import com.example.transervmedical.presentation.screens.components.ReusableComponents.EditTextEmailOutline
import com.example.transervmedical.presentation.screens.components.ReusableComponents.EditTextPasswordOutline
import com.example.transervmedical.presentation.viewmodel.RegistrationViewModel

@Composable
fun SignInScreen(
    navHostController: NavHostController,
    registrationViewModel: RegistrationViewModel = hiltViewModel()
) {
    val state = registrationViewModel.state
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = context) {
        registrationViewModel.validationEvents.collect { event ->
            when (event) {
                is RegistrationViewModel.ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "Added user with email: ${state.email}",
                        Toast.LENGTH_SHORT
                    ).show()
                    navHostController.navigate(route = Screen.Dashboard.route)
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
        }
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
                value = state.email,
                onValueChange = { registrationViewModel.onEvent(RegistrationFormEvent.EmailChanged(it)) },
                label = "Email",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                })
            )
            if (state.emailError != null) {
                Text(
                    text = state.emailError,
                    color = MaterialTheme.colors.error
                )
            }

            EditTextEmailOutline(
                value = state.phoneNumber,
                onValueChange = { registrationViewModel.onEvent(RegistrationFormEvent.PhoneNumberChanged(it)) },
                label = "Phone Number",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                })
            )

            if (state.phoneNumberError != null) {
                Text(
                    text = state.phoneNumberError,
                    color = MaterialTheme.colors.error
                )
            }
            EditTextPasswordOutline(
                value = state.password,
                onValueChange = { registrationViewModel.onEvent(RegistrationFormEvent.PasswordChanged(it)) },
                label = "Password",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                })
            )
            if (state.passwordError != null) {
                Text(
                    text = state.passwordError,
                    color = MaterialTheme.colors.error
                )
            }
            EditTextPasswordOutline(
                value = state.repeatedPassword,
                onValueChange = { registrationViewModel.onEvent(RegistrationFormEvent.RepeatedPasswordChanged(it)) },
                label = "Repeated Password",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)
            )
            if (state.repeatedPasswordError != null) {
                Text(
                    text = state.repeatedPasswordError,
                    color = MaterialTheme.colors.error
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            BlueButton(
                onClick = {
                    registrationViewModel.addUserInDb(
                        User(
                            email = state.email,
                            phoneNumber = state.phoneNumber,
                            password = state.password,
                            repeatedPassword = state.repeatedPassword
                        )
                    )
                    registrationViewModel.onEvent(RegistrationFormEvent.Submit)
                },
                buttonText = "Sign up"
            )
        }
    }
}