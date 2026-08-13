package com.kidslab.physicslab.ui.labs.mass

import androidx.compose.runtime.Composable
import com.kidslab.physicslab.data.repository.PhysicsLabRepository
import com.kidslab.physicslab.data.seed.TopicSeed
import com.kidslab.physicslab.domain.engine.ForceMassEngine
import com.kidslab.physicslab.domain.model.IntensityLevel
import com.kidslab.physicslab.ui.components.PeoeStage
import com.kidslab.physicslab.ui.labs.GenericLabScreen
import com.kidslab.physicslab.ui.labs.LabComputation
import com.kidslab.physicslab.ui.labs.forcemass.ForceMassCart
import com.kidslab.physicslab.ui.labs.forcemass.LevelPicker

private fun levelFromValue(v: Double) = when (v.toInt()) {
    1 -> IntensityLevel.LOW
    3 -> IntensityLevel.HIGH
    else -> IntensityLevel.MEDIUM
}

@Composable
fun MassLabScreen(repository: PhysicsLabRepository, onBack: () -> Unit) {
    GenericLabScreen(
        topicId = TopicSeed.MASA,
        labTitle = "Masa",
        labEmoji = "⚖️",
        repository = repository,
        onBack = onBack,
        predictOptionsFor = {
            listOf("Acelerará más rápido", "Acelerará más lento")
        },
        computeResult = { _, params ->
            val massLevel = levelFromValue(params["massLevel"] ?: 1.0)
            val forceLevel = levelFromValue(params["forceLevel"] ?: 2.0)
            val result = ForceMassEngine.simulate(forceLevel, massLevel)
            LabComputation(
                resultValue = result.accelerationMs2,
                resultUnitSymbol = "m/s²",
                resultDescriptionEs = "Con masa ${massLevel.displayEs.lowercase()}, el carrito acelera a ${ForceMassEngine.formattedAcceleration(result.accelerationMs2)}.",
                matchesOption = { option ->
                    val lowMass = massLevel == IntensityLevel.LOW
                    (option?.contains("más rápido") == true && lowMass) ||
                        (option?.contains("más lento") == true && !lowMass)
                }
            )
        },
        controls = { _, viewModel ->
            LevelPicker(
                labelEs = "Nivel de masa",
                current = viewModel.paramValues["massLevel"] ?: 1.0,
                onChange = { viewModel.updateParam("massLevel", it) }
            )
        },
        visualize = { _, viewModel ->
            ForceMassCart(
                forceLevel = levelFromValue(viewModel.paramValues["forceLevel"] ?: 2.0),
                massLevel = levelFromValue(viewModel.paramValues["massLevel"] ?: 1.0),
                animate = viewModel.stage != PeoeStage.PREDICT,
                trigger = viewModel.animationTrigger
            )
        }
    )
}
