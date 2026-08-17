package com.kidslab.physicslab.ui.labs.machines

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
import com.kidslab.physicslab.domain.engine.SimpleMachineEngine
import com.kidslab.physicslab.ui.components.ParameterSliderRow
import com.kidslab.physicslab.ui.components.PeoeStage
import com.kidslab.physicslab.ui.components.UnitChip
import com.kidslab.physicslab.ui.components.drawEmoji
import com.kidslab.physicslab.ui.labs.GenericLabScreen
import com.kidslab.physicslab.ui.labs.LabComputation

@Composable
fun SimpleMachinesLabScreen(repository: PhysicsLabRepository, onBack: () -> Unit) {
    GenericLabScreen(
        topicId = TopicSeed.MAQUINAS,
        labTitle = "Máquinas simples",
        labEmoji = "⚙️",
        repository = repository,
        onBack = onBack,
        predictOptionsFor = { experiment ->
            when (experiment.id) {
                "maq_01" -> listOf("Necesitarás menos fuerza", "Necesitarás más fuerza")
                "maq_02" -> listOf("La mitad de fuerza", "El doble de fuerza")
                else -> listOf("Necesitarás menos fuerza", "Necesitarás más fuerza")
            }
        },
        computeResult = { experiment, params ->
            val loadWeight = params["loadWeight"] ?: 20.0
            when (experiment.id) {
                "maq_01" -> {
                    val effortArm = params["effortArm"] ?: 1.5
                    val loadArm = params["loadArm"] ?: 0.5
                    val result = SimpleMachineEngine.lever(loadWeight, effortArm, loadArm)
                    LabComputation(
                        resultValue = result.requiredForceN, resultUnitSymbol = "N",
                        resultDescriptionEs = "Necesitas ${SimpleMachineEngine.formattedForce(result.requiredForceN)} para levantar la carga (ventaja: ${SimpleMachineEngine.formattedAdvantage(result.mechanicalAdvantage)}).",
                        matchesOption = { it?.contains("menos fuerza") == true && effortArm > loadArm }
                    )
                }
                "maq_02" -> {
                    val ropes = (params["ropes"] ?: 1.0).toInt()
                    val result = SimpleMachineEngine.pulley(loadWeight, ropes)
                    LabComputation(
                        resultValue = result.requiredForceN, resultUnitSymbol = "N",
                        resultDescriptionEs = "Con $ropes cuerda(s), necesitas ${SimpleMachineEngine.formattedForce(result.requiredForceN)}.",
                        matchesOption = { option -> (option?.contains("mitad") == true) == (ropes == 2) }
                    )
                }
                else -> {
                    val rampLength = params["rampLength"] ?: 3.0
                    val height = params["height"] ?: 1.0
                    val result = SimpleMachineEngine.inclinedPlane(loadWeight, rampLength, height)
                    LabComputation(
                        resultValue = result.requiredForceN, resultUnitSymbol = "N",
                        resultDescriptionEs = "Con una rampa de ${String.format("%.1f", rampLength)} m, necesitas ${SimpleMachineEngine.formattedForce(result.requiredForceN)}.",
                        matchesOption = { it?.contains("menos fuerza") == true && rampLength > height }
                    )
                }
            }
        },
        controls = { experiment, viewModel ->
            when (experiment.id) {
                "maq_01" -> ParameterSliderRow("Brazo de esfuerzo", "m", viewModel.paramValues["effortArm"] ?: 1.5, 0.5, 3.0, { viewModel.updateParam("effortArm", it) })
                "maq_02" -> ParameterSliderRow(
                    "Cuerdas de apoyo", "", viewModel.paramValues["ropes"] ?: 1.0, 1.0, 3.0,
                    onValueChange = { viewModel.updateParam("ropes", kotlin.math.round(it)) },
                    steps = 1,
                    decimals = 0
                )
                else -> ParameterSliderRow("Largo de la rampa", "m", viewModel.paramValues["rampLength"] ?: 3.0, 2.0, 6.0, { viewModel.updateParam("rampLength", it) })
            }
        },
        visualize = { experiment, viewModel ->
            when (experiment.id) {
                "maq_01" -> LeverVisual(
                    effortArm = viewModel.paramValues["effortArm"] ?: 1.5,
                    loadArm = viewModel.paramValues["loadArm"] ?: 0.5,
                    animate = viewModel.stage != PeoeStage.PREDICT,
                    trigger = viewModel.animationTrigger
                )
                "maq_02" -> PulleyVisual(
                    ropes = (viewModel.paramValues["ropes"] ?: 1.0).toInt(),
                    animate = viewModel.stage != PeoeStage.PREDICT,
                    trigger = viewModel.animationTrigger
                )
                else -> InclinedPlaneVisual(
                    rampLength = viewModel.paramValues["rampLength"] ?: 3.0,
                    animate = viewModel.stage != PeoeStage.PREDICT,
                    trigger = viewModel.animationTrigger
                )
            }
        }
    )
}

@Composable
private fun LeverVisual(effortArm: Double, loadArm: Double, animate: Boolean, trigger: Int) {
    val progress = remember { Animatable(0f) }
    LaunchedEffect(trigger, animate, effortArm, loadArm) {
        if (animate) { progress.snapTo(0f); progress.animateTo(1f, tween(1200)) } else progress.snapTo(0f)
    }
    val tilt = (30f * progress.value) * (if (effortArm > loadArm) 1f else -1f)
    Column {
        Canvas(modifier = Modifier.fillMaxWidth().height(140.dp)) {
            val cx = size.width / 2
            val cy = size.height / 2
            // El brazo ocupa casi todo el ancho del lienzo, en vez de un tramo fijo pequeño,
            // para que el balanceo se note claramente sin importar el tamaño de pantalla.
            val armSpan = size.width * 0.38f
            drawEmoji("🔺", center = Offset(cx, cy + 26f), size = 24.dp)
            val leftY = cy - tilt
            val rightY = cy + tilt
            drawLine(Color(0xFFFF8A3D), Offset(cx - armSpan, leftY), Offset(cx + armSpan, rightY), strokeWidth = 8f)
            drawEmoji("🖐️", center = Offset(cx - armSpan, leftY), size = 34.dp)
            drawEmoji("📦", center = Offset(cx + armSpan, rightY), size = 36.dp)
        }
        UnitChip(value = "Brazo esfuerzo: ${String.format("%.1f", effortArm)} m")
    }
}

@Composable
private fun PulleyVisual(ropes: Int, animate: Boolean, trigger: Int) {
    val progress = remember { Animatable(0f) }
    LaunchedEffect(trigger, animate, ropes) {
        if (animate) { progress.snapTo(0f); progress.animateTo(1f, tween(1400)) } else progress.snapTo(0f)
    }
    val speedFactor = 1f / ropes
    Column {
        Canvas(modifier = Modifier.fillMaxWidth().height(180.dp)) {
            val cx = size.width / 2
            val wheelY = 24f
            val restY = size.height - 30f
            // La caja sube usando casi todo el alto del lienzo, en vez de un rango fijo muy chico,
            // para que el movimiento se note bien sin importar el tamaño de pantalla.
            val liftRange = (size.height - wheelY - 60f) * speedFactor
            val liftY = restY - (liftRange * progress.value)
            drawCircle(Color(0xFF8C6BFF), radius = 16f, center = Offset(cx, wheelY), style = androidx.compose.ui.graphics.drawscope.Stroke(width = 4f))
            drawLine(Color(0xFFB9C6E0), Offset(cx, wheelY), Offset(cx, liftY), strokeWidth = 4f)
            drawEmoji("📦", center = Offset(cx, liftY + 14f), size = 40.dp)
        }
        UnitChip(value = "$ropes cuerda(s) de apoyo")
    }
}

@Composable
private fun InclinedPlaneVisual(rampLength: Double, animate: Boolean, trigger: Int) {
    val progress = remember { Animatable(0f) }
    LaunchedEffect(trigger, animate, rampLength) {
        if (animate) { progress.snapTo(0f); progress.animateTo(1f, tween(1400)) } else progress.snapTo(0f)
    }
    // Con la misma altura, una rampa más larga es más tendida (el punto de apoyo se aleja hacia
    // la izquierda) y una rampa corta es más empinada, así el dibujo cambia con el slider.
    val lengthFraction = ((rampLength - 2.0) / (6.0 - 2.0)).coerceIn(0.0, 1.0).toFloat()
    Column {
        Canvas(modifier = Modifier.fillMaxWidth().height(120.dp)) {
            val baseY = size.height - 10f
            val topX = size.width - 20f
            val topY = 20f
            val steepBaseX = topX - size.width * 0.3f
            val shallowBaseX = 16f
            val baseX = steepBaseX + (shallowBaseX - steepBaseX) * lengthFraction
            drawLine(Color(0xFFE3C08C), Offset(baseX, baseY), Offset(topX, topY), strokeWidth = 10f)
            val x = baseX + (topX - baseX) * progress.value
            val y = baseY + (topY - baseY) * progress.value
            drawEmoji("📦", center = Offset(x, y - 16f), size = 32.dp)
        }
        UnitChip(value = "Rampa: ${String.format("%.1f", rampLength)} m")
    }
}
