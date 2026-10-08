package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val NumberooLightColorScheme = lightColorScheme(
    primary = KangarooGold,
    onPrimary = Color.White,
    primaryContainer = KangarooGoldLight,
    onPrimaryContainer = TextDarkBrown,
    secondary = PlayfulSkyBlue,
    onSecondary = Color.White,
    secondaryContainer = PlayfulSkyBlueLight,
    onSecondaryContainer = TextDarkBrown,
    tertiary = PlayfulMintGreen,
    onTertiary = Color.White,
    tertiaryContainer = PlayfulMintGreenLight,
    onTertiaryContainer = TextDarkBrown,
    background = BackgroundWarmCream,
    onBackground = TextDarkBrown,
    surface = SurfaceWarmWhite,
    onSurface = TextDarkBrown,
    surfaceVariant = SurfaceVariantWarm,
    onSurfaceVariant = TextMutedBrown
)

private val NumberooDarkColorScheme = darkColorScheme(
    primary = KangarooGoldLight,
    onPrimary = TextDarkBrown,
    primaryContainer = KangarooGoldDark,
    onPrimaryContainer = Color.White,
    secondary = PlayfulSkyBlue,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF01579B),
    onSecondaryContainer = Color.White,
    tertiary = PlayfulMintGreenLight,
    onTertiary = TextDarkBrown,
    tertiaryContainer = Color(0xFF1B5E20),
    onTertiaryContainer = Color.White,
    background = Color(0xFF1A1612),
    onBackground = Color(0xFFFFE0B2),
    surface = Color(0xFF26201B),
    onSurface = Color(0xFFFFE0B2),
    surfaceVariant = Color(0xFF3E3228),
    onSurfaceVariant = Color(0xFFFFCC80)
)

@Composable
fun NumberooTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) NumberooDarkColorScheme else NumberooLightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
