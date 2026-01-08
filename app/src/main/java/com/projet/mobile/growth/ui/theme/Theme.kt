package com.projet.mobile.growth.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.projet.mobile.growth.ui.theme.Typography

private val LightColorScheme = lightColorScheme(
    primary = ThemePrimary,
    secondary = ThemeSecondary,
    background = BackgroundLight,
    surface = SurfaceLight
)

private val DarkColorScheme = darkColorScheme(
    primary = ThemeSecondary,
    background = BackgroundDark,
    surface = SurfaceDark
)

@Composable
fun GrowthTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}