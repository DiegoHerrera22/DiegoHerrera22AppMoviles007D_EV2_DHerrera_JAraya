package com.pasteleria1000sabores.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Defines the colour palette and overall styling for the Pastelería 1000
 * Sabores app.  Jetpack Compose's Material 3 system allows you to supply
 * a custom [MaterialTheme] at the top of your composable hierarchy so that
 * colours, typography and shapes are consistent across screens.
 */
private val LightColors = lightColorScheme(
    primary = Color(0xFFFFC107),
    onPrimary = Color(0xFF000000),
    secondary = Color(0xFF795548),
    onSecondary = Color(0xFFFFFFFF),
    error = Color(0xFFB00020),
    onError = Color(0xFFFFFFFF),
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF000000),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF000000)
)

@Composable
fun Pasteleria1000SaboresTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}