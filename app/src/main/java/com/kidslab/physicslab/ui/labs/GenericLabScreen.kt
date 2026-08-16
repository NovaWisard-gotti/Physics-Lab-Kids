package com.kidslab.physicslab.ui.labs

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kidslab.physicslab.data.local.entity.ExperimentEntity
import com.kidslab.physicslab.data.repository.PhysicsLabRepository
import com.kidslab.physicslab.ui.components.CompareCard
import com.kidslab.physicslab.ui.components.ExplainCard
import com.kidslab.physicslab.ui.components.LabScaffold
import com.kidslab.physicslab.ui.components.PeoeStage
import com.kidslab.physicslab.ui.components.PeoeStepper
import com.kidslab.physicslab.ui.components.PredictCard
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GenericLabScreen(
    topicId: String,
    labTitle: String,
    labEmoji: String,
    repository: PhysicsLabRepository,
    onBack: () -> Unit,
    predictOptionsFor: (ExperimentEntity) -> List<String>,
    computeResult: (ExperimentEntity, Map<String, Double>) -> LabComputation,
    controls: @Composable (ExperimentEntity, GuidedLabViewModel) -> Unit,
    visualize: @Composable (ExperimentEntity, GuidedLabViewModel) -> Unit
) {
    val viewModel: GuidedLabViewModel = viewModel(
        factory = GuidedLabViewModel.Factory(repository, topicId, computeResult)
    )
    val experiments by viewModel.experiments.collectAsState()

    LabScaffold(title = labTitle, emoji = labEmoji, onBack = onBack) { modifier ->
        if (experiments.isEmpty()) {
            Column(modifier = modifier.fillMaxSize().padding(24.dp)) {
                Text("Cargando experimentos… ⏳", style = MaterialTheme.typography.bodyLarge)
            }
            return@LabScaffold
        }

        val experiment = experiments[viewModel.currentIndex]

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(experiments) { exp ->
                    val selected = exp.id == experiment.id
                    FilterChip(
                        selected = selected,
                        onClick = { viewModel.selectExperiment(experiments.indexOf(exp)) },
                        label = { Text(exp.titleEs, fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.Normal) },
                        shape = RoundedCornerShape(50),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = androidx.compose.ui.graphics.Color.White
                        )
                    )
                }
            }

            Text(experiment.titleEs, style = MaterialTheme.typography.headlineMedium)
            Text(experiment.instructionEs, style = MaterialTheme.typography.bodyLarge)

            PeoeStepper(current = viewModel.stage, modifier = Modifier.fillMaxWidth())

            AnimatedContent(
                targetState = viewModel.stage,
                transitionSpec = {
                    (fadeIn(tween(260)) + slideInHorizontally(tween(260)) { it / 6 }) togetherWith
                        (fadeOut(tween(180)) + slideOutHorizontally(tween(180)) { -it / 6 })
                },
                label = "peoeStage"
            ) { stage ->
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    when (stage) {
                        PeoeStage.PREDICT -> {
                            PredictCard(
                                questionEs = experiment.predictQuestionEs,
                                options = predictOptionsFor(experiment),
                                onOptionChosen = { viewModel.choosePrediction(it) }
                            )
                        }
                        PeoeStage.EXPERIMENT -> {
                            controls(experiment, viewModel)
                            visualize(experiment, viewModel)
                            BigActionButton(text = "Iniciar experimento 🚀", onClick = { viewModel.runExperiment() })
                        }
                        PeoeStage.OBSERVE -> {
                            visualize(experiment, viewModel)
                            viewModel.computation?.let { result ->
                                CompareCard(
                                    predictedEs = viewModel.predictedOption ?: "",
                                    resultDescriptionEs = result.resultDescriptionEs,
                                    matched = result.matchesOption(viewModel.predictedOption)
                                )
                            }
                            BigActionButton(text = "Continuar ➡️", onClick = { viewModel.goToExplain() })
                        }
                        PeoeStage.EXPLAIN -> {
                            ExplainCard(explanationEs = experiment.explanationEs)
                            if (viewModel.currentIndex < experiments.size - 1) {
                                BigActionButton(text = "Siguiente experimento ✨", onClick = { viewModel.nextExperiment() })
                            } else {
                                BigActionButton(text = "Terminar laboratorio 🏁", onClick = onBack)
                            }
                            OutlinedButton(
                                onClick = { viewModel.replay() },
                                shape = RoundedCornerShape(50),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Reiniciar 🔄")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BigActionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold)
    }
}
