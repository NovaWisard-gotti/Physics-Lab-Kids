package com.kidslab.physicslab.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kidslab.physicslab.ui.theme.CelebrationGradient
import com.kidslab.physicslab.ui.theme.LabOrange

@Composable
fun UnitChip(value: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.primary
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun CompareCard(
    predictedEs: String,
    resultDescriptionEs: String,
    matched: Boolean,
    modifier: Modifier = Modifier
) {
    val bounce = remember { Animatable(0.6f) }
    LaunchedEffect(matched) {
        bounce.snapTo(0.6f)
        bounce.animateTo(1f, animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy))
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(if (matched) CelebrationGradient else androidx.compose.ui.graphics.Brush.linearGradient(listOf(LabOrange.copy(alpha = 0.18f), LabOrange.copy(alpha = 0.10f))))
            .padding(18.dp)
    ) {
        Column {
            Text(
                "Tu predicción vs. lo que pasó",
                style = MaterialTheme.typography.titleLarge,
                color = if (matched) androidx.compose.ui.graphics.Color.White else MaterialTheme.colorScheme.onSurface
            )
            Text(
                "Predijiste: $predictedEs",
                style = MaterialTheme.typography.bodyLarge,
                color = if (matched) androidx.compose.ui.graphics.Color.White else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                "Resultado: $resultDescriptionEs",
                style = MaterialTheme.typography.bodyLarge,
                color = if (matched) androidx.compose.ui.graphics.Color.White else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = if (matched) "¡Tu predicción fue correcta! 🎉" else "Esta vez el resultado fue distinto a tu predicción, ¡eso también es aprender! 🧠",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.ExtraBold,
                color = if (matched) androidx.compose.ui.graphics.Color.White else LabOrange,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .scale(bounce.value)
            )
        }
    }
}

@Composable
fun ExplainCard(explanationEs: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(18.dp)
    ) {
        Column {
            Row {
                Text("💡", fontSize = 24.sp)
                Text(
                    "¿Por qué pasó esto?",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Text(explanationEs, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Composable
fun ParameterSliderRow(
    labelEs: String,
    unitSymbol: String,
    value: Double,
    min: Double,
    max: Double,
    onValueChange: (Double) -> Unit,
    enabled: Boolean = true
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(labelEs, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
            Surface(shape = RoundedCornerShape(50), color = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)) {
                Text(
                    text = "${String.format("%.1f", value)} $unitSymbol",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }
        }
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toDouble()) },
            valueRange = min.toFloat()..(if (max > min) max.toFloat() else (min + 1.0).toFloat()),
            enabled = enabled && max > min,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}
