package com.kidslab.physicslab.ui.notebook

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kidslab.physicslab.data.repository.PhysicsLabRepository
import com.kidslab.physicslab.ui.components.LabScaffold

@Composable
fun NotebookScreen(repository: PhysicsLabRepository, onBack: () -> Unit) {
    val viewModel: NotebookViewModel = viewModel(factory = NotebookViewModel.Factory(repository))
    val badges by viewModel.badges.collectAsState()
    val earnedIds by viewModel.earnedBadgeIds.collectAsState()
    val completedCount by viewModel.completedCount.collectAsState()

    LabScaffold(title = "Mi cuaderno de científico", emoji = "📓", onBack = onBack) { modifier ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("Mi progreso", style = MaterialTheme.typography.titleLarge)
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Experimentos completados: $completedCount / 24", style = MaterialTheme.typography.bodyLarge)
                    LinearProgressIndicator(
                        progress = { (completedCount / 24f).coerceIn(0f, 1f) },
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                    )
                }
            }

            Text("Mis insignias", style = MaterialTheme.typography.titleLarge)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                badges.chunked(2).forEach { rowBadges ->
                    androidx.compose.foundation.layout.Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        rowBadges.forEach { badge ->
                            val earned = earnedIds.contains(badge.id)
                            Card(
                                modifier = Modifier.weight(1f),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (earned) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                                )
                            ) {
                                Column(Modifier.padding(12.dp)) {
                                    Text(badge.iconEmoji, fontSize = 26.sp)
                                    Text(
                                        badge.nameEs,
                                        style = MaterialTheme.typography.labelLarge,
                                        color = if (earned) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        if (earned) "¡Conseguida!" else badge.criteriaEs,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = if (earned) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                        if (rowBadges.size == 1) {
                            androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            Text("Preguntas rápidas", style = MaterialTheme.typography.titleLarge)
            QuizSection(viewModel)
        }
    }
}

@Composable
private fun QuizSection(viewModel: NotebookViewModel) {
    if (viewModel.quizQuestions.isEmpty()) {
        Text("Cargando preguntas…", style = MaterialTheme.typography.bodyLarge)
        return
    }
    if (viewModel.quizFinished) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("¡Terminaste el quiz!", style = MaterialTheme.typography.titleLarge)
                Text(
                    "Respondiste bien ${viewModel.quizScore} de ${viewModel.quizQuestions.size}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Button(onClick = viewModel::restartQuiz, modifier = Modifier.padding(top = 12.dp)) {
                    Text("Jugar de nuevo")
                }
            }
        }
        return
    }

    val question = viewModel.quizQuestions[viewModel.currentQuizIndex]
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("Pregunta ${viewModel.currentQuizIndex + 1} de ${viewModel.quizQuestions.size}", style = MaterialTheme.typography.labelLarge)
            Text(question.questionEs, style = MaterialTheme.typography.bodyLarge)

            listOf("A" to question.optionAEs, "B" to question.optionBEs, "C" to question.optionCEs).forEach { (key, text) ->
                val selected = viewModel.quizSelectedOption == key
                val isCorrectAnswer = key == question.correctOption
                val showFeedback = viewModel.quizSelectedOption != null
                val color = when {
                    showFeedback && isCorrectAnswer -> Color(0xFF33C481)
                    showFeedback && selected && !isCorrectAnswer -> Color(0xFFFF5C5C)
                    else -> MaterialTheme.colorScheme.surfaceVariant
                }
                Button(
                    onClick = { viewModel.selectQuizOption(key) },
                    enabled = viewModel.quizSelectedOption == null,
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = color),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text)
                }
            }

            if (viewModel.quizSelectedOption != null) {
                OutlinedButton(onClick = viewModel::nextQuizQuestion, modifier = Modifier.fillMaxWidth()) {
                    Text("Siguiente pregunta")
                }
            }
        }
    }
}
