package com.kidslab.physicslab.ui.labs.energy

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
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
import com.kidslab.physicslab.domain.engine.EnergyEngine
import com.kidslab.physicslab.ui.components.ParameterSliderRow
import com.kidslab.physicslab.ui.components.PeoeStage
import com.kidslab.physicslab.ui.components.UnitChip
import com.kidslab.physicslab.ui.components.drawEmoji
import com.kidslab.physicslab.ui.labs.GenericLabScreen
import com.kidslab.physicslab.ui.labs.LabComputation

@Composable
fun EnergyLabScreen(repository: PhysicsLabRepository, onBack: () -> Unit) {
    GenericLabScreen(
        topicId = TopicSeed.ENERGIA,
        labTitle = "Energía",
        labEmoji = "🎢",
        repository = repository,
        onBack = onBack,
        predictOptionsFor = { experiment ->
            when (experiment.id) {
                "ene_01" -> listOf("Tiene más energía de altura", "Tiene más energía de movimiento")
                "ene_02" -> listOf("Llegará más rápido", "Llegará más lento")
                else -> listOf("A la mitad del camino", "Al principio del recorrido")
            }
        },
        computeResult = { _, params ->
            val startHeight = params["startHeight"] ?: 2.0
            val mass = params["mass"] ?: 2.0
            val bottom = EnergyEngine.energyAt(mass, startHeight, 0.0)
            LabComputation(
                resultValue = bottom.speedMs,
                resultUnitSymbol = "m/s",
                resultDescriptionEs = "Abajo de la colina, el carrito alcanza ${EnergyEngine.formattedSpeed(bottom.speedMs)}, toda su energía es de movimiento.",
                matchesOption = { option ->
                    option?.contains("altura") == true || option?.contains("más rápido") == true || option?.contains("mitad") == true
                }
            )
        },
        controls = { experiment, viewModel ->
            ParameterSliderRow(
                "Altura de la colina", "m", viewModel.paramValues["startHeight"] ?: 2.0,
                1.0, 10.0, { viewModel.updateParam("startHeight", it) }
            )
        },
        visualize = { _, viewModel ->
            RollerCoaster(
                startHeight = viewModel.paramValues["startHeight"] ?: 2.0,
                mass = viewModel.paramValues["mass"] ?: 2.0,
                animate = viewModel.stage != PeoeStage.PREDICT,
                trigger = viewModel.animationTrigger
            )
        }
    )
}

@Composable
private fun RollerCoaster(startHeight: Double, mass: Double, animate: Boolean, trigger: Int) {
    val progress = remember { Animatable(0f) }
    LaunchedEffect(trigger, animate) {
        if (animate) { progress.snapTo(0f); progress.animateTo(1f, tween(1800)) } else progress.snapTo(0f)
    }
    val currentHeight = startHeight * (1 - progress.value)
    val point = EnergyEngine.energyAt(mass, startHeight, currentHeight)

    Column {
        Canvas(modifier = Modifier.fillMaxWidth().height(120.dp)) {
            val groundY = size.height - 10f
            val topY = 10f
            // pista curva simple (valle)
            drawLine(Color(0xFFB9C6E0), Offset(0f, topY), Offset(size.width / 2, groundY), strokeWidth = 6f)
            drawLine(Color(0xFFB9C6E0), Offset(size.width / 2, groundY), Offset(size.width, topY), strokeWidth = 6f)
            val cartX = size.width * progress.value
            val cartY = if (progress.value <= 0.5f) topY + (groundY - topY) * (progress.value / 0.5f) else groundY - (groundY - topY) * ((progress.value - 0.5f) / 0.5f)
            drawEmoji("🚋", center = Offset(cartX, cartY), sizePx = 30f)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EnergyBar(labelEs = "Potencial", fraction = point.potentialFraction, color = Color(0xFF8C6BFF))
            EnergyBar(labelEs = "Cinética", fraction = point.kineticFraction, color = Color(0xFF33C481))
        }
        UnitChip(value = "Velocidad: ${EnergyEngine.formattedSpeed(point.speedMs)}")
    }
}

@Composable
private fun EnergyBar(labelEs: String, fraction: Float, color: Color) {
    Column {
        Text(labelEs, style = MaterialTheme.typography.labelLarge)
        Canvas(modifier = Modifier.width(60.dp).height(80.dp)) {
            val barHeight = size.height * fraction.coerceIn(0f, 1f)
            drawRect(Color(0xFFE7ECFA), topLeft = Offset(0f, 0f), size = androidx.compose.ui.geometry.Size(size.width, size.height))
            drawRect(color, topLeft = Offset(0f, size.height - barHeight), size = androidx.compose.ui.geometry.Size(size.width, barHeight))
        }
    }
}
