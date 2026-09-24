package com.kokoro.ai.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = KokoroViolet,
    onPrimary = KokoroSurface,
    primaryContainer = KokoroVioletLight,
    onPrimaryContainer = KokoroTextPrimary,
    secondary = KokoroPink,
    onSecondary = KokoroSurface,
    secondaryContainer = KokoroPinkLight,
    onSecondaryContainer = KokoroTextPrimary,
    tertiary = KokoroGold,
    background = KokoroBackground,
    onBackground = KokoroTextPrimary,
    surface = KokoroSurface,
    onSurface = KokoroTextPrimary,
    surfaceVariant = KokoroBackground,
    onSurfaceVariant = KokoroTextSecondary
)

@Composable
fun KokoroTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = KokoroBackground.toArgb()
            window.navigationBarColor = KokoroBackground.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
