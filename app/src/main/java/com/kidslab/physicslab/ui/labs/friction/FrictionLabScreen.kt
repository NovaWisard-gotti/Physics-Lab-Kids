package com.kidslab.physicslab.ui.labs.friction

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
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
import com.kidslab.physicslab.domain.engine.FrictionEngine
import com.kidslab.physicslab.domain.model.SurfaceType
import com.kidslab.physicslab.ui.components.ParameterSliderRow
import com.kidslab.physicslab.ui.components.PeoeStage
import com.kidslab.physicslab.ui.components.UnitChip
import com.kidslab.physicslab.ui.labs.GenericLabScreen
import com.kidslab.physicslab.ui.labs.LabComputation

private fun surfacesFor(experimentId: String): List<SurfaceType> = when (experimentId) {
    "fri_01" -> listOf(SurfaceType.ICE, SurfaceType.WOOD)
    "fri_02" -> listOf(SurfaceType.WOOD, SurfaceType.CARPET)
    else -> listOf(SurfaceType.ICE, SurfaceType.WOOD, SurfaceType.CARPET)
}

@Composable
fun FrictionLabScreen(repository: PhysicsLabRepository, onBack: () -> Unit) {
    GenericLabScreen(
        topicId = TopicSeed.FRICCION,
        labTitle = "Fricción",
        labEmoji = "🧊",
        repository = repository,
        onBack = onBack,
        predictOptionsFor = { experiment ->
            when (experiment.id) {
                "fri_01" -> listOf("Llega más lejos en hielo", "Llega más lejos en madera")
                "fri_02" -> listOf("Llega más lejos en madera", "Llega más lejos en alfombra")
                else -> listOf("Hielo, madera, alfombra", "Alfombra, madera, hielo")
            }
        },
        computeResult = { experiment, params ->
            val v = params["initialVelocity"] ?: 4.0
            val results = surfacesFor(experiment.id).map { FrictionEngine.simulate(it, v) }
            val ordered = results.sortedByDescending { it.stoppingDistanceM }
            val description = ordered.joinToString(", ") { "${it.surface.displayEs}: ${FrictionEngine.formattedDistance(it.stoppingDistanceM)}" }
            LabComputation(
                resultValue = ordered.first().stoppingDistanceM,
                resultUnitSymbol = "m",
                resultDescriptionEs = "De mayor a menor distancia: $description.",
                matchesOption = { option ->
                    when {
                        option?.contains("hielo") == true -> ordered.first().surface == SurfaceType.ICE
                        option?.contains("madera") == true && experiment.id != "fri_03" -> ordered.first().surface == SurfaceType.WOOD
                        option?.contains("Hielo, madera, alfombra") == true -> ordered.map { it.surface } == listOf(SurfaceType.ICE, SurfaceType.WOOD, SurfaceType.CARPET)
                        else -> false
                    }
                }
            )
        },
        controls = { _, viewModel ->
            ParameterSliderRow(
                "Impulso inicial", "m/s", viewModel.paramValues["initialVelocity"] ?: 4.0,
                1.0, 8.0, { viewModel.updateParam("initialVelocity", it) }
            )
        },
        visualize = { experiment, viewModel ->
            FrictionLanes(
                surfaces = surfacesFor(experiment.id),
                initialVelocity = viewModel.paramValues["initialVelocity"] ?: 4.0,
                animate = viewModel.stage != PeoeStage.PREDICT,
                trigger = viewModel.animationTrigger
            )
        }
    )
}

@Composable
private fun FrictionLanes(surfaces: List<SurfaceType>, initialVelocity: Double, animate: Boolean, trigger: Int) {
    val progress = remember { Animatable(0f) }
    LaunchedEffect(trigger, animate) {
        if (animate) { progress.snapTo(0f); progress.animateTo(1f, tween(1400)) } else progress.snapTo(0f)
    }
    val maxDistance = 40.0
    Column {
        Canvas(modifier = Modifier.fillMaxWidth().height(140.dp)) {
            val laneHeight = size.height / surfaces.size
            surfaces.forEachIndexed { index, surface ->
                val y = laneHeight * index + laneHeight / 2
                val laneColor = when (surface) {
                    SurfaceType.ICE -> Color(0xFFBFE3FF)
                    SurfaceType.WOOD -> Color(0xFFE3C08C)
                    SurfaceType.CARPET -> Color(0xFFE0A9C9)
                }
                drawLine(laneColor, Offset(0f, y), Offset(size.width, y), strokeWidth = 20f)
                val d = FrictionEngine.simulate(surface, initialVelocity).stoppingDistanceM
                val xFraction = ((d / maxDistance).coerceIn(0.0, 1.0) * progress.value).toFloat()
                val x = xFraction * (size.width - 30f)
                drawCircle(Color(0xFF1F2532), radius = 12f, center = Offset(x + 15f, y))
            }
        }
        Column {
            surfaces.forEach { UnitChip(value = "${it.emoji} ${it.displayEs}") }
        }
    }
}
