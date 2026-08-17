package com.kidslab.physicslab.ui.labs.forcemass

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kidslab.physicslab.data.repository.PhysicsLabRepository
import com.kidslab.physicslab.data.seed.TopicSeed
import com.kidslab.physicslab.domain.engine.ForceMassEngine
import com.kidslab.physicslab.domain.model.IntensityLevel
import com.kidslab.physicslab.ui.components.PeoeStage
import com.kidslab.physicslab.ui.components.UnitChip
import com.kidslab.physicslab.ui.components.drawEmoji
import com.kidslab.physicslab.ui.labs.GenericLabScreen
import com.kidslab.physicslab.ui.labs.LabComputation

private fun levelFromValue(v: Double) = when (v.toInt()) {
    1 -> IntensityLevel.LOW
    3 -> IntensityLevel.HIGH
    else -> IntensityLevel.MEDIUM
}

@Composable
fun ForceLabScreen(repository: PhysicsLabRepository, onBack: () -> Unit) {
    GenericLabScreen(
        topicId = TopicSeed.FUERZA,
        labTitle = "Fuerza",
        labEmoji = "💪",
        repository = repository,
        onBack = onBack,
        predictOptionsFor = {
            listOf("Arrancará más rápido", "Arrancará más lento")
        },
        computeResult = { _, params ->
            val forceLevel = levelFromValue(params["forceLevel"] ?: 1.0)
            val massLevel = levelFromValue(params["massLevel"] ?: 2.0)
            val result = ForceMassEngine.simulate(forceLevel, massLevel)
            LabComputation(
                resultValue = result.accelerationMs2,
                resultUnitSymbol = "m/s²",
                resultDescriptionEs = "Con fuerza ${forceLevel.displayEs.lowercase()}, el carrito acelera a ${ForceMassEngine.formattedAcceleration(result.accelerationMs2)}.",
                matchesOption = { option ->
                    val highForce = forceLevel == IntensityLevel.HIGH || forceLevel == IntensityLevel.MEDIUM
                    (option?.contains("más rápido") == true && highForce) ||
                        (option?.contains("más lento") == true && !highForce)
                }
            )
        },
        controls = { _, viewModel ->
            LevelPicker(
                labelEs = "Nivel de fuerza",
                current = viewModel.paramValues["forceLevel"] ?: 1.0,
                onChange = { viewModel.updateParam("forceLevel", it) }
            )
        },
        visualize = { _, viewModel ->
            ForceMassCart(
                forceLevel = levelFromValue(viewModel.paramValues["forceLevel"] ?: 1.0),
                massLevel = levelFromValue(viewModel.paramValues["massLevel"] ?: 2.0),
                animate = viewModel.stage != PeoeStage.PREDICT,
                trigger = viewModel.animationTrigger
            )
        }
    )
}

@Composable
fun LevelPicker(labelEs: String, current: Double, onChange: (Double) -> Unit) {
    Text(labelEs, style = androidx.compose.material3.MaterialTheme.typography.bodyLarge)
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        listOf(1.0 to "Baja", 2.0 to "Media", 3.0 to "Alta").forEach { (value, label) ->
            Button(
                onClick = { onChange(value) },
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = if (current == value) androidx.compose.material3.MaterialTheme.colorScheme.primary
                    else androidx.compose.material3.MaterialTheme.colorScheme.surfaceVariant
                )
            ) { Text(label) }
        }
    }
}

@Composable
fun ForceMassCart(forceLevel: IntensityLevel, massLevel: IntensityLevel, animate: Boolean, trigger: Int) {
    val progress = remember { Animatable(0f) }
    LaunchedEffect(trigger, animate) {
        if (animate) {
            progress.snapTo(0f)
            progress.animateTo(1f, tween(1400))
        } else progress.snapTo(0f)
    }
    val result = ForceMassEngine.simulate(forceLevel, massLevel)
    val normalized = ForceMassEngine.normalizedAcceleration(result)
    val cartSize = 48f + massLevel.factor.toFloat() * 18f

    androidx.compose.foundation.layout.Column {
        Canvas(modifier = Modifier.fillMaxWidth().height(120.dp)) {
            val y = size.height / 2
            val x = (normalized * progress.value) * (size.width - 60f)
            drawLine(Color(0xFFB9C6E0), Offset(0f, y), Offset(size.width, y), strokeWidth = 6f)
            drawEmoji("🚗", center = Offset(x + cartSize / 2, y), sizePx = cartSize)
        }
        UnitChip(value = "a = ${ForceMassEngine.formattedAcceleration(result.accelerationMs2)}")
    }
}
