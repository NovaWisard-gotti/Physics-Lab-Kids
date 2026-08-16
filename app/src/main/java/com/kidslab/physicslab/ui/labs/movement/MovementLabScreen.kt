package com.kidslab.physicslab.ui.labs.movement

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.kidslab.physicslab.data.repository.PhysicsLabRepository
import com.kidslab.physicslab.domain.engine.MovementEngine
import com.kidslab.physicslab.ui.components.ParameterSliderRow
import com.kidslab.physicslab.ui.components.UnitChip
import com.kidslab.physicslab.ui.components.PeoeStage
import com.kidslab.physicslab.ui.components.drawEmoji
import com.kidslab.physicslab.ui.labs.GenericLabScreen
import com.kidslab.physicslab.ui.labs.GuidedLabViewModel
import com.kidslab.physicslab.ui.labs.LabComputation
import com.kidslab.physicslab.data.seed.TopicSeed

@Composable
fun MovementLabScreen(repository: PhysicsLabRepository, onBack: () -> Unit) {
    GenericLabScreen(
        topicId = TopicSeed.MOVIMIENTO,
        labTitle = "Movimiento",
        labEmoji = "🏃",
        repository = repository,
        onBack = onBack,
        predictOptionsFor = { experiment ->
            when (experiment.id) {
                "mov_01" -> listOf("Recorrerá más distancia", "Recorrerá menos distancia")
                "mov_02" -> listOf("Sí, la distancia se duplica", "No, la distancia no cambia igual")
                else -> listOf("El carrito rápido llega más lejos", "El carrito lento llega más lejos")
            }
        },
        computeResult = { _, params ->
            val v = params["initialVelocity"] ?: 0.0
            val t = params["time"] ?: 0.0
            val result = MovementEngine.simulate(v, t)
            LabComputation(
                resultValue = result.distanceM,
                resultUnitSymbol = "m",
                resultDescriptionEs = "El carrito recorrió ${MovementEngine.formattedDistance(result.distanceM)} en $t s.",
                matchesOption = { option ->
                    when {
                        option?.contains("más distancia") == true -> v >= 2.5
                        option?.contains("menos distancia") == true -> v < 2.5
                        option?.contains("se duplica") == true -> true
                        else -> true
                    }
                }
            )
        },
        controls = { experiment, viewModel ->
            Column {
                ParameterSliderRow(
                    labelEs = "Velocidad inicial", unitSymbol = "m/s",
                    value = viewModel.paramValues["initialVelocity"] ?: 0.0,
                    min = 0.5, max = 8.0,
                    onValueChange = { viewModel.updateParam("initialVelocity", it) }
                )
                ParameterSliderRow(
                    labelEs = "Tiempo", unitSymbol = "s",
                    value = viewModel.paramValues["time"] ?: 0.0,
                    min = 1.0, max = 10.0,
                    onValueChange = { viewModel.updateParam("time", it) }
                )
            }
        },
        visualize = { experiment, viewModel ->
            MovementTrack(
                velocity = viewModel.paramValues["initialVelocity"] ?: 0.0,
                time = viewModel.paramValues["time"] ?: 0.0,
                animate = viewModel.stage != PeoeStage.PREDICT,
                trigger = viewModel.animationTrigger
            )
        }
    )
}

@Composable
private fun MovementTrack(velocity: Double, time: Double, animate: Boolean, trigger: Int) {
    val progress = remember { Animatable(0f) }
    LaunchedEffect(trigger, animate) {
        if (animate) {
            progress.snapTo(0f)
            progress.animateTo(1f, animationSpec = tween(1400))
        } else {
            progress.snapTo(0f)
        }
    }
    val distance = MovementEngine.simulate(velocity, time).distanceM

    Column {
        Canvas(modifier = Modifier.fillMaxWidth().height(120.dp)) {
            val trackY = size.height / 2
            drawLine(Color(0xFFB9C6E0), Offset(0f, trackY), Offset(size.width, trackY), strokeWidth = 6f)
            val maxDistance = 80.0 // metros equivalentes al ancho completo de la pista visual
            val xFraction = ((distance / maxDistance).coerceIn(0.0, 1.0) * progress.value).toFloat()
            val x = xFraction * (size.width - 40f)
            drawEmoji("🚗", center = Offset(x + 20f, trackY), sizePx = 48f)
        }
        UnitChip(value = "${(distance * 100).toInt() / 100.0} m recorridos")
    }
}
