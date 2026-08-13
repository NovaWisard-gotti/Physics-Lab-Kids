package com.kidslab.physicslab.ui.labs.speed

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kidslab.physicslab.data.repository.PhysicsLabRepository
import com.kidslab.physicslab.data.seed.TopicSeed
import com.kidslab.physicslab.domain.engine.MovementEngine
import com.kidslab.physicslab.ui.components.ParameterSliderRow
import com.kidslab.physicslab.ui.components.PeoeStage
import com.kidslab.physicslab.ui.components.UnitChip
import com.kidslab.physicslab.ui.labs.GenericLabScreen
import com.kidslab.physicslab.ui.labs.LabComputation

private val RACER_COLORS = listOf(Color(0xFF2F6FED), Color(0xFFFF8A3D), Color(0xFF33C481))

@Composable
fun SpeedLabScreen(repository: PhysicsLabRepository, onBack: () -> Unit) {
    GenericLabScreen(
        topicId = TopicSeed.VELOCIDAD,
        labTitle = "Velocidad",
        labEmoji = "🚀",
        repository = repository,
        onBack = onBack,
        predictOptionsFor = { experiment ->
            when (experiment.id) {
                "vel_01" -> listOf("Gana el carrito A", "Gana el carrito B")
                "vel_02" -> listOf("Llegan en orden: rápido, medio, lento", "Llegan todos juntos")
                else -> listOf("Sí, la distancia se duplica", "No, se mantiene igual")
            }
        },
        computeResult = { experiment, params ->
            val time = params["time"] ?: 5.0
            when (experiment.id) {
                "vel_03" -> {
                    val v = params["velocity"] ?: 0.0
                    val d = MovementEngine.simulate(v, time).distanceM
                    LabComputation(d, "m", "Con $v m/s durante $time s, el carrito recorrió ${MovementEngine.formattedDistance(d)}.") { true }
                }
                else -> {
                    val a = params["velocityA"] ?: 0.0
                    val b = params["velocityB"] ?: 0.0
                    val c = params["velocityC"]
                    val dA = MovementEngine.simulate(a, time).distanceM
                    val dB = MovementEngine.simulate(b, time).distanceM
                    val dC = c?.let { MovementEngine.simulate(it, time).distanceM }
                    val winner = listOfNotNull("A" to dA, "B" to dB, dC?.let { "C" to it })
                        .maxByOrNull { it.second }?.first ?: "A"
                    LabComputation(
                        resultValue = maxOf(dA, dB, dC ?: 0.0),
                        resultUnitSymbol = "m",
                        resultDescriptionEs = "El carrito $winner llegó más lejos.",
                        matchesOption = { option ->
                            option?.contains(if (winner == "A") "carrito A" else "carrito B") == true ||
                                option?.contains("orden") == true
                        }
                    )
                }
            }
        },
        controls = { experiment, viewModel ->
            Column {
                if (experiment.id == "vel_03") {
                    ParameterSliderRow("Velocidad", "m/s", viewModel.paramValues["velocity"] ?: 0.0, 1.0, 10.0, { viewModel.updateParam("velocity", it) })
                } else {
                    ParameterSliderRow("Velocidad A", "m/s", viewModel.paramValues["velocityA"] ?: 0.0, 1.0, 8.0, { viewModel.updateParam("velocityA", it) })
                    ParameterSliderRow("Velocidad B", "m/s", viewModel.paramValues["velocityB"] ?: 0.0, 1.0, 8.0, { viewModel.updateParam("velocityB", it) })
                    if (viewModel.paramValues.containsKey("velocityC")) {
                        ParameterSliderRow("Velocidad C", "m/s", viewModel.paramValues["velocityC"] ?: 0.0, 1.0, 8.0, { viewModel.updateParam("velocityC", it) })
                    }
                }
            }
        },
        visualize = { experiment, viewModel ->
            RaceTrack(
                velocities = listOfNotNull(
                    viewModel.paramValues["velocityA"],
                    viewModel.paramValues["velocityB"],
                    viewModel.paramValues["velocityC"]
                ).ifEmpty { listOfNotNull(viewModel.paramValues["velocity"]) },
                time = viewModel.paramValues["time"] ?: 5.0,
                animate = viewModel.stage != PeoeStage.PREDICT,
                trigger = viewModel.animationTrigger
            )
        }
    )
}

@Composable
private fun RaceTrack(velocities: List<Double>, time: Double, animate: Boolean, trigger: Int) {
    val progress = remember { Animatable(0f) }
    LaunchedEffect(trigger, animate) {
        if (animate) {
            progress.snapTo(0f)
            progress.animateTo(1f, tween(1400))
        } else progress.snapTo(0f)
    }
    val maxDistance = 60.0
    Column {
        Canvas(modifier = Modifier.fillMaxWidth().height(140.dp)) {
            val laneHeight = size.height / velocities.size.coerceAtLeast(1)
            velocities.forEachIndexed { index, v ->
                val y = laneHeight * index + laneHeight / 2
                drawLine(Color(0xFFB9C6E0), Offset(0f, y), Offset(size.width, y), strokeWidth = 5f)
                val d = MovementEngine.simulate(v, time).distanceM
                val xFraction = ((d / maxDistance).coerceIn(0.0, 1.0) * progress.value).toFloat()
                val x = xFraction * (size.width - 30f)
                drawCircle(RACER_COLORS[index % RACER_COLORS.size], radius = 16f, center = Offset(x + 15f, y))
            }
        }
        UnitChip(value = "Carrera de ${velocities.size} carritos")
    }
}
