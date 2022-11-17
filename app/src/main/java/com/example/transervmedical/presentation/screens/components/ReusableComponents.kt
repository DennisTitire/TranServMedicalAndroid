package com.example.transervmedical.presentation.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

object ReusableComponents {

    @Composable
    fun BlueButton(
        onClick: () -> Unit,
        buttonText: String
    ) {
        Button(
            onClick = { onClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = buttonText,
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.SemiBold),
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    fun EditTextEmailOutline(
        value: String,
        onValueChange: (String) -> Unit,
        label: String,
        maxLines: Int = Int.MAX_VALUE,
        leadingIcon: @Composable (() -> Unit)? = null,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
        keyboardActions: KeyboardActions = KeyboardActions.Default
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label) },
            shape = RoundedCornerShape(15.dp),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            maxLines = maxLines,
            leadingIcon = leadingIcon,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )
    }

    @Composable
    fun EditTextPasswordOutline(
        value: String,
        onValueChange: (String) -> Unit,
        label: String,
        visibility: MutableState<Boolean> = remember { mutableStateOf(false) },
        maxLines : Int = Int.MAX_VALUE,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
        keyboardActions: KeyboardActions = KeyboardActions.Default
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label) },
            shape = RoundedCornerShape(15.dp),
            visualTransformation = if (visibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            trailingIcon = {
                val image = if (visibility.value)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                val imageDescription = if (visibility.value) "Hide password" else "Show password"
                IconButton(
                    onClick = { visibility.value = !visibility.value },
                    modifier = Modifier.padding(end = 12.dp)
                ) {
                    Icon(imageVector = image, contentDescription = imageDescription)
                }
            },
            maxLines = maxLines,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )
    }
}