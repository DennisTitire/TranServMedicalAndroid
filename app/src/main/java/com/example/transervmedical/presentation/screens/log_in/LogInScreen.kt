package com.example.transervmedical.presentation.screens.log_in

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.transervmedical.R
import com.example.transervmedical.navigation.Screen
import com.example.transervmedical.presentation.screens.components.ReusableComponents.BlueButton
import com.example.transervmedical.presentation.screens.components.ReusableComponents.EditTextEmailOutline
import com.example.transervmedical.presentation.screens.components.ReusableComponents.EditTextPasswordOutline
import com.example.transervmedical.ui.theme.Blue

@Composable
fun LogInScreen(
    navHostController: NavHostController,
) {
    var emailTextField by remember { mutableStateOf(("")) }
    var passwordTextField by remember { mutableStateOf(("")) }

    val passwordVisible: MutableState<Boolean> = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Image(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.transervmedical_logo),
            contentDescription = "Logo Image"
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "Welcome",
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            )
        )
        EditTextEmailOutline(
            value = emailTextField,
            onValueChange = { emailTextField = it },
            label = "Email",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            })
        )
        EditTextPasswordOutline(
            value = passwordTextField,
            onValueChange = { passwordTextField = it },
            label = "Password",
            visibility = passwordVisible,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)
        )
        Spacer(modifier = Modifier.height(24.dp))

        BlueButton(
            onClick = { navHostController.navigate(Screen.Dashboard.route) },
            buttonText = "Log in",
        )
        Spacer(modifier = Modifier.height(90.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                modifier = Modifier.width(150.dp),
                color = Color.Gray, thickness = 1.dp
            )
            Text(text = "or", color = Color.Gray)
            Divider(
                modifier = Modifier.width(150.dp),
                color = Color.Gray, thickness = 1.dp
            )
        }
        Spacer(modifier = Modifier.height(26.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "Don't have an account?", color = Color.Gray)
            Text(
                modifier = Modifier.clickable {
                    navHostController.navigate(route = Screen.SignIn.route)
                },
                text = "Sign up",
                color = Blue,
                fontSize = 18.sp,
                style = MaterialTheme.typography.h2
            )
        }
    }

}