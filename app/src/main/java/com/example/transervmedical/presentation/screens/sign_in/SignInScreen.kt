package com.example.transervmedical.presentation.screens.sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.transervmedical.domain.model.User
import com.example.transervmedical.navigation.Screen
import com.example.transervmedical.presentation.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    navHostController: NavHostController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    var emailTextField by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var passwordTextField by remember { mutableStateOf("") }
    var repeatedPasswordTextField by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(start = 12.dp, end = 12.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            modifier = Modifier.padding(start = 24.dp),
            text = "SignIn",
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
        )
        OutlinedTextField(
            value = emailTextField,
            onValueChange = { emailTextField = it },
            label = { Text(text = "Email") },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text(text = "Phone Number") },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        OutlinedTextField(
            value = passwordTextField,
            onValueChange = { passwordTextField = it },
            label = { Text(text = "Password") },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )

        OutlinedTextField(
            value = repeatedPasswordTextField,
            onValueChange = { repeatedPasswordTextField = it },
            label = { Text(text = "Repeated Password") },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )

        OutlinedButton(
            onClick = {
                scope.launch {
                    userViewModel.addUser(
                        User(
                            email = emailTextField,
                            phoneNumber = phoneNumber,
                            password = passwordTextField,
                            repeatedPassword = repeatedPasswordTextField
                        )
                    )
                }
                navHostController.navigate(route = Screen.Home.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
        ) {
            Text(
                text = "SignIn",
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.SemiBold),
                textAlign = TextAlign.Center
            )
        }
    }
}