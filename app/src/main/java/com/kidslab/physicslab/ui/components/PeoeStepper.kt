package com.kidslab.physicslab.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/** Las cuatro etapas del método de cada experimento guiado. */
enum class PeoeStage(val labelEs: String, val emoji: String) {
    PREDICT("Predice", "🤔"),
    EXPERIMENT("Experimenta", "🧪"),
    OBSERVE("Observa", "👀"),
    EXPLAIN("Explica", "💡")
}

@Composable
fun PeoeStepper(current: PeoeStage, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            PeoeStage.values().forEachIndexed { index, stage ->
                val active = stage == current
                val done = stage.ordinal < current.ordinal
                val bubbleSize by animateDpAsState(
                    targetValue = if (active) 40.dp else 32.dp,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                    label = "bubbleSize"
                )
                val color = when {
                    active -> MaterialTheme.colorScheme.primary
                    done -> MaterialTheme.colorScheme.tertiary
                    else -> Color(0xFFD7DEEF)
                }
                Box(
                    modifier = Modifier.size(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(bubbleSize)
                            .clip(CircleShape)
                            .background(color),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(if (done) "✓" else stage.emoji, color = Color.White)
                    }
                }
                if (index < PeoeStage.values().size - 1) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(if (done) MaterialTheme.colorScheme.tertiary else Color(0xFFD7DEEF))
                    )
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 6.dp)) {
            PeoeStage.values().forEach { stage ->
                Text(
                    text = stage.labelEs,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = if (stage == current) FontWeight.ExtraBold else FontWeight.SemiBold,
                    color = if (stage == current) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}
