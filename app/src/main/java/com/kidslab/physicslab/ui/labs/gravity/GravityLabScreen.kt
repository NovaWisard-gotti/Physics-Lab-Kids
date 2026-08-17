package com.kidslab.physicslab.ui.labs.gravity

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
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
import com.kidslab.physicslab.domain.engine.GravityEngine
import com.kidslab.physicslab.domain.model.PlanetMode
import com.kidslab.physicslab.ui.components.ParameterSliderRow
import com.kidslab.physicslab.ui.components.PeoeStage
import com.kidslab.physicslab.ui.components.UnitChip
import com.kidslab.physicslab.ui.components.drawEmoji
import com.kidslab.physicslab.ui.labs.GenericLabScreen
import com.kidslab.physicslab.ui.labs.LabComputation

@Composable
fun GravityLabScreen(repository: PhysicsLabRepository, onBack: () -> Unit) {
    GenericLabScreen(
        topicId = TopicSeed.GRAVEDAD,
        labTitle = "Gravedad",
        labEmoji = "🍎",
        repository = repository,
        onBack = onBack,
        predictOptionsFor = { experiment ->
            when (experiment.id) {
                "gra_01" -> listOf("Llega primero la pelota pesada", "Llegan las dos al mismo tiempo", "Llega primero la pelota liviana")
                "gra_02" -> listOf("Tardará más tiempo", "Tardará menos tiempo")
                else -> listOf("Cae más rápido en la Tierra", "Cae más rápido en la Luna")
            }
        },
        computeResult = { experiment, params ->
            when (experiment.id) {
                "gra_01" -> {
                    val h = params["height"] ?: 5.0
                    val massA = params["massA"] ?: 0.2
                    val massB = params["massB"] ?: 3.0
                    val (a, b) = GravityEngine.compareTwoObjects(h, PlanetMode.EARTH, "Liviana" to massA, "Pesada" to massB)
                    LabComputation(
                        resultValue = a.fallTimeS, resultUnitSymbol = "s",
                        resultDescriptionEs = "Ambas pelotas tardaron ${GravityEngine.formattedTime(a.fallTimeS)} en caer, ¡sin importar su masa!",
                        matchesOption = { it?.contains("mismo tiempo") == true }
                    )
                }
                "gra_02" -> {
                    val h = params["height"] ?: 4.0
                    val result = GravityEngine.simulateFall("Objeto", 1.0, h, PlanetMode.EARTH)
                    LabComputation(
                        resultValue = result.fallTimeS, resultUnitSymbol = "s",
                        resultDescriptionEs = "Desde $h m tardó ${GravityEngine.formattedTime(result.fallTimeS)} en caer.",
                        matchesOption = { it?.contains("más tiempo") == true }
                    )
                }
                else -> {
                    val h = params["height"] ?: 5.0
                    val earth = GravityEngine.simulateFall("Objeto", 1.0, h, PlanetMode.EARTH)
                    val moon = GravityEngine.simulateFall("Objeto", 1.0, h, PlanetMode.MOON)
                    LabComputation(
                        resultValue = moon.fallTimeS, resultUnitSymbol = "s",
                        resultDescriptionEs = "En la Tierra tardó ${GravityEngine.formattedTime(earth.fallTimeS)}, en la Luna tardó ${GravityEngine.formattedTime(moon.fallTimeS)}.",
                        matchesOption = { it?.contains("rápido en la Tierra") == true }
                    )
                }
            }
        },
        controls = { experiment, viewModel ->
            Column {
                ParameterSliderRow(
                    "Altura", "m", viewModel.paramValues["height"] ?: 5.0,
                    1.0, if (experiment.id == "gra_02") 15.0 else 10.0,
                    { viewModel.updateParam("height", it) }
                )
            }
        },
        visualize = { experiment, viewModel ->
            when (experiment.id) {
                "gra_01" -> TwoObjectFall(
                    heightM = viewModel.paramValues["height"] ?: 5.0,
                    planet = PlanetMode.EARTH,
                    animate = viewModel.stage != PeoeStage.PREDICT,
                    trigger = viewModel.animationTrigger
                )
                "gra_03" -> EarthMoonFall(
                    heightM = viewModel.paramValues["height"] ?: 5.0,
                    animate = viewModel.stage != PeoeStage.PREDICT,
                    trigger = viewModel.animationTrigger
                )
                else -> SingleFall(
                    heightM = viewModel.paramValues["height"] ?: 5.0,
                    planet = PlanetMode.EARTH,
                    animate = viewModel.stage != PeoeStage.PREDICT,
                    trigger = viewModel.animationTrigger
                )
            }
        }
    )
}

@Composable
private fun TwoObjectFall(heightM: Double, planet: PlanetMode, animate: Boolean, trigger: Int) {
    val progress = remember { Animatable(0f) }
    LaunchedEffect(trigger, animate) {
        if (animate) { progress.snapTo(0f); progress.animateTo(1f, tween(1400)) } else progress.snapTo(0f)
    }
    Column {
        Canvas(modifier = Modifier.fillMaxWidth().height(160.dp)) {
            val groundY = size.height - 10f
            listOf(0.3f to "🏓", 0.7f to "🎳").forEach { (xFrac, emoji) ->
                val y = (groundY - 20f) * progress.value
                drawEmoji(emoji, center = Offset(size.width * xFrac, y + 16f), sizePx = 46f)
            }
            drawLine(Color(0xFF8C6BFF), Offset(0f, groundY), Offset(size.width, groundY), strokeWidth = 6f)
        }
        UnitChip(value = "Altura: ${heightM} m")
    }
}

@Composable
private fun EarthMoonFall(heightM: Double, animate: Boolean, trigger: Int) {
    val earthProgress = remember { Animatable(0f) }
    val moonProgress = remember { Animatable(0f) }
    LaunchedEffect(trigger, animate) {
        if (animate) {
            earthProgress.snapTo(0f); moonProgress.snapTo(0f)
        } else {
            earthProgress.snapTo(0f); moonProgress.snapTo(0f)
        }
    }
    LaunchedEffect(trigger, animate) {
        if (animate) earthProgress.animateTo(1f, tween(1200))
    }
    LaunchedEffect(trigger, animate) {
        if (animate) moonProgress.animateTo(1f, tween(2800))
    }
    Column {
        Canvas(modifier = Modifier.fillMaxWidth().height(160.dp)) {
            val groundY = size.height - 10f
            val earthY = (groundY - 20f) * earthProgress.value
            val moonY = (groundY - 20f) * moonProgress.value
            drawEmoji("🍎", center = Offset(size.width * 0.3f, earthY + 16f), sizePx = 44f)
            drawEmoji("🍎", center = Offset(size.width * 0.7f, moonY + 16f), sizePx = 44f)
            drawLine(Color(0xFFB9C6E0), Offset(0f, groundY), Offset(size.width, groundY), strokeWidth = 6f)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            UnitChip(value = "🌍 Tierra")
            UnitChip(value = "🌕 Luna")
        }
    }
}

@Composable
private fun SingleFall(heightM: Double, planet: PlanetMode, animate: Boolean, trigger: Int) {
    val progress = remember { Animatable(0f) }
    LaunchedEffect(trigger, animate, planet) {
        if (animate) { progress.snapTo(0f); progress.animateTo(1f, tween(if (planet == PlanetMode.MOON) 2400 else 1400)) } else progress.snapTo(0f)
    }
    Column {
        Canvas(modifier = Modifier.fillMaxWidth().height(160.dp)) {
            val groundY = size.height - 10f
            val y = (groundY - 20f) * progress.value
            drawEmoji("🍎", center = Offset(size.width / 2, y + 18f), sizePx = 50f)
            drawLine(Color(0xFF8C6BFF), Offset(0f, groundY), Offset(size.width, groundY), strokeWidth = 6f)
        }
        UnitChip(value = "${planet.emoji} ${planet.displayEs} · g = ${planet.gravity} m/s²")
    }
}
