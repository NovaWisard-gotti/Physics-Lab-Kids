package com.kidslab.physicslab.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/** Las cuatro etapas del método de cada experimento guiado. */
enum class PeoeStage(val labelEs: String, val emoji: String) {
    PREDICT("Predice", "\uD83E\uDD14"),
    EXPERIMENT("Experimenta", "\uD83E\uDDEA"),
    OBSERVE("Observa", "\uD83D\uDC40"),
    EXPLAIN("Explica", "\uD83D\uDCA1")
}

@Composable
fun PeoeStepper(current: PeoeStage, modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceEvenly) {
        PeoeStage.values().forEach { stage ->
            val active = stage == current
            val done = stage.ordinal < current.ordinal
            val color = when {
                active -> MaterialTheme.colorScheme.primary
                done -> MaterialTheme.colorScheme.tertiary
                else -> Color.LightGray
            }
            Box(
                modifier = Modifier.size(28.dp).background(color, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(stage.emoji, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
    Row(modifier = modifier.padding(top = 4.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
        PeoeStage.values().forEach { stage ->
            Text(
                text = stage.labelEs,
                style = MaterialTheme.typography.labelLarge,
                color = if (stage == current) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
