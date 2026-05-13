package com.example.nearbyeats_app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val NearbyEatsColorScheme = lightColorScheme(
    primary = Brown40,
    secondary = BrownGrey40,
    tertiary = Amber40,
    background = PeachBackground,
    surface = CardBackground,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = TextDark,
    onSurface = TextDark,
)

@Composable
fun NearbyEats_AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = NearbyEatsColorScheme,
        typography = Typography,
        content = content
    )
}
