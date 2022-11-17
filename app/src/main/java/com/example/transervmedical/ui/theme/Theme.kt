package com.example.transervmedical.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Blue,
    primaryVariant = Purple700,
    secondary = Teal200,
    onPrimary = Color.White
)

private val LightColorPalette = lightColors(
    primary = Blue,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = Color.White

    /* Other default colors to override
    background = Color.White,
        surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
        onBackground = Color.Black,
        onSurface = Color.Black,
    */
)

@Composable
fun TranServMedicalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = if (darkTheme) Color.Black else Color.White,
        )
    }

    MaterialTheme(
        colors = colors,
        typography = if (darkTheme) Typography.copy(TextStyle(color = Color.White)) else Typography.copy(TextStyle(color = Color.Black)),
        shapes = Shapes,
        content = content
    )
}