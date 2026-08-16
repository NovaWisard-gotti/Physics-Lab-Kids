package com.kidslab.physicslab.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val LabBlue = Color(0xFF3D7BFF)
val LabBlueDark = Color(0xFF1B4FBF)
val LabOrange = Color(0xFFFF9433)
val LabGreen = Color(0xFF2ED47A)
val LabYellow = Color(0xFFFFCC33)
val LabPurple = Color(0xFF9B6BFF)
val LabPink = Color(0xFFFF5FA2)
val LabRed = Color(0xFFFF5C5C)
val LabTeal = Color(0xFF2BC7C4)
val LabBackground = Color(0xFFF2F6FF)
val LabSurface = Color(0xFFFFFFFF)
val LabText = Color(0xFF232946)

/** Un color vivo por cada laboratorio, en el mismo orden en que se muestran. */
val TopicColors = listOf(LabBlue, LabPink, LabOrange, LabGreen, LabPurple, LabTeal, LabRed, LabYellow)

/** Un segundo tono para crear degradés alegres a partir de [TopicColors]. */
val TopicColorsLight = listOf(
    Color(0xFF7AA6FF),
    Color(0xFFFF9BC4),
    Color(0xFFFFC178),
    Color(0xFF7CE8AA),
    Color(0xFFC6ADFF),
    Color(0xFF7FE5E3),
    Color(0xFFFF9B9B),
    Color(0xFFFFE28A)
)

fun topicGradient(index: Int): Brush {
    val base = TopicColors[index % TopicColors.size]
    val light = TopicColorsLight[index % TopicColorsLight.size]
    return Brush.linearGradient(listOf(light, base))
}

val BackgroundGradient = Brush.verticalGradient(listOf(Color(0xFFEFF4FF), Color(0xFFE3ECFF)))
val CelebrationGradient = Brush.linearGradient(listOf(LabGreen, LabTeal))
