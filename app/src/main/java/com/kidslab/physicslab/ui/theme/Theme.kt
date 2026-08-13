package com.kidslab.physicslab.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val LightColors = lightColorScheme(
    primary = LabBlue,
    onPrimary = LabSurface,
    secondary = LabOrange,
    onSecondary = LabSurface,
    tertiary = LabPurple,
    background = LabBackground,
    onBackground = LabText,
    surface = LabSurface,
    onSurface = LabText,
    error = LabRed
)

private val DarkColors = darkColorScheme(
    primary = LabBlue,
    onPrimary = LabSurface,
    secondary = LabOrange,
    background = LabText,
    onBackground = LabBackground,
    surface = Color(0xFF2A3142),
    onSurface = LabBackground,
    error = LabRed
)

val LabTypography = Typography(
    headlineLarge = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 30.sp),
    headlineMedium = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp),
    titleLarge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
    bodyLarge = TextStyle(fontWeight = FontWeight.Normal, fontSize = 17.sp),
    bodyMedium = TextStyle(fontWeight = FontWeight.Normal, fontSize = 15.sp),
    labelLarge = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
)

@Composable
fun FisicaLabTheme(useDarkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colorScheme = if (useDarkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colorScheme,
        typography = LabTypography,
        content = content
    )
}
