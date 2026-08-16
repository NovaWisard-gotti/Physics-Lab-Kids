package com.kidslab.physicslab.ui.notebook

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
            SectionTitle("Mi progreso 📈")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(18.dp)
            ) {
                Column {
                    Text(
                        "Experimentos completados: $completedCount / 24",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    LinearProgressIndicator(
                        progress = { (completedCount / 24f).coerceIn(0f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .clip(RoundedCornerShape(50)),
                        color = MaterialTheme.colorScheme.tertiary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }

            SectionTitle("Mis insignias 🏅")
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                badges.chunked(2).forEach { rowBadges ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        rowBadges.forEach { badge ->
                            val earned = earnedIds.contains(badge.id)
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(
                                        if (earned) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.surfaceVariant
                                    )
                                    .padding(14.dp)
                            ) {
                                Column {
                                    Text(badge.iconEmoji, fontSize = 28.sp)
                                    Text(
                                        badge.nameEs,
                                        style = MaterialTheme.typography.labelLarge,
                                        color = if (earned) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                    Text(
                                        if (earned) "¡Conseguida! ✅" else badge.criteriaEs,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = if (earned) Color.White.copy(alpha = 0.92f) else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                        if (rowBadges.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            SectionTitle("Preguntas rápidas ❓")
            QuizSection(viewModel)
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
}

@Composable
private fun QuizSection(viewModel: NotebookViewModel) {
    if (viewModel.quizQuestions.isEmpty()) {
        Text("Cargando preguntas… ⏳", style = MaterialTheme.typography.bodyLarge)
        return
    }
    if (viewModel.quizFinished) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.tertiary)
                .padding(20.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text("🎉 ¡Terminaste el quiz! 🎉", style = MaterialTheme.typography.titleLarge, color = Color.White)
                Text(
                    "Respondiste bien ${viewModel.quizScore} de ${viewModel.quizQuestions.size}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Button(
                    onClick = viewModel::restartQuiz,
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier.padding(top = 14.dp)
                ) {
                    Text("Jugar de nuevo 🔁", color = MaterialTheme.colorScheme.tertiary, fontWeight = FontWeight.ExtraBold)
                }
            }
        }
        return
    }

    val question = viewModel.quizQuestions[viewModel.currentQuizIndex]
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(18.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                "Pregunta ${viewModel.currentQuizIndex + 1} de ${viewModel.quizQuestions.size}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(question.questionEs, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)

            listOf("A" to question.optionAEs, "B" to question.optionBEs, "C" to question.optionCEs).forEach { (key, text) ->
                val selected = viewModel.quizSelectedOption == key
                val isCorrectAnswer = key == question.correctOption
                val showFeedback = viewModel.quizSelectedOption != null
                val color = when {
                    showFeedback && isCorrectAnswer -> Color(0xFF2ED47A)
                    showFeedback && selected && !isCorrectAnswer -> Color(0xFFFF5C5C)
                    else -> MaterialTheme.colorScheme.surfaceVariant
                }
                val textColor = if (showFeedback && (isCorrectAnswer || selected)) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                Button(
                    onClick = { viewModel.selectQuizOption(key) },
                    enabled = viewModel.quizSelectedOption == null,
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = color, disabledContainerColor = color),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text, color = textColor, fontWeight = FontWeight.SemiBold)
                }
            }

            if (viewModel.quizSelectedOption != null) {
                OutlinedButton(
                    onClick = viewModel::nextQuizQuestion,
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Siguiente pregunta ➡️")
                }
            }
        }
    }
}
