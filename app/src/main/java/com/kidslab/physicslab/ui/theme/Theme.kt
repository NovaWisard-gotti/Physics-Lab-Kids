package com.kidslab.physicslab.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
    surfaceVariant = Color(0xFFE7EDFF),
    onSurfaceVariant = LabText,
    error = LabRed
)

private val DarkColors = darkColorScheme(
    primary = LabBlue,
    onPrimary = LabSurface,
    secondary = LabOrange,
    tertiary = LabPurple,
    background = LabText,
    onBackground = LabBackground,
    surface = Color(0xFF2A3142),
    onSurface = LabBackground,
    error = LabRed
)

val LabTypography = Typography(
    headlineLarge = TextStyle(fontWeight = FontWeight.Black, fontSize = 32.sp),
    headlineMedium = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 25.sp),
    titleLarge = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 21.sp),
    bodyLarge = TextStyle(fontWeight = FontWeight.Medium, fontSize = 17.sp),
    bodyMedium = TextStyle(fontWeight = FontWeight.Normal, fontSize = 15.sp),
    labelLarge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp)
)

val LabShapes = Shapes(
    extraSmall = RoundedCornerShape(10.dp),
    small = RoundedCornerShape(14.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(28.dp),
    extraLarge = RoundedCornerShape(36.dp)
)

@Composable
fun FisicaLabTheme(useDarkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colorScheme = if (useDarkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colorScheme,
        typography = LabTypography,
        shapes = LabShapes,
        content = content
    )
}
