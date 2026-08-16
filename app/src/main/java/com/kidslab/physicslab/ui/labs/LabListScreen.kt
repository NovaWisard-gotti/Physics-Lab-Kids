package com.kidslab.physicslab.ui.labs

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kidslab.physicslab.data.local.entity.PhysicsTopicEntity
import com.kidslab.physicslab.data.repository.PhysicsLabRepository
import com.kidslab.physicslab.ui.components.bouncyClickable
import com.kidslab.physicslab.ui.navigation.PhysicsLabRoutes
import com.kidslab.physicslab.ui.theme.BackgroundGradient
import com.kidslab.physicslab.ui.theme.topicGradient

@Composable
fun LabListScreen(
    repository: PhysicsLabRepository,
    onOpenTopic: (String) -> Unit,
    onOpenNotebook: () -> Unit
) {
    val topics by repository.observeTopics().collectAsState(initial = emptyList())

    Scaffold(containerColor = Color.Transparent) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGradient)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                item { LabListHeader(onOpenNotebook = onOpenNotebook) }
                itemsIndexed(topics, key = { _, topic -> topic.id }) { index, topic ->
                    AnimatedEntry(index = index) {
                        TopicCard(
                            topic = topic,
                            colorIndex = index,
                            onClick = { onOpenTopic(PhysicsLabRoutes.routeForTopic(topic.id)) }
                        )
                    }
                }
                item { androidx.compose.foundation.layout.Spacer(Modifier.size(8.dp)) }
            }
        }
    }
}

@Composable
private fun LabListHeader(onOpenNotebook: () -> Unit) {
    Column(modifier = Modifier.padding(bottom = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "¡Hola, científico! 👋",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    "Elige un laboratorio y descubre cómo funciona el mundo 🔭",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .bouncyClickable(onClick = onOpenNotebook),
                contentAlignment = Alignment.Center
            ) {
                Text("📓", fontSize = 26.sp)
            }
        }
    }
}

@Composable
private fun AnimatedEntry(index: Int, content: @Composable () -> Unit) {
    val visibleState = androidx.compose.runtime.remember { androidx.compose.animation.core.MutableTransitionState(false) }
    visibleState.targetState = true
    androidx.compose.animation.AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(animationSpec = tween(320, delayMillis = index * 60)) +
            slideInVertically(
                animationSpec = tween(320, delayMillis = index * 60),
                initialOffsetY = { it / 4 }
            )
    ) {
        content()
    }
}

@Composable
private fun TopicCard(topic: PhysicsTopicEntity, colorIndex: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(topicGradient(colorIndex))
            .bouncyClickable(onClick = onClick)
            .padding(18.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.35f)),
                contentAlignment = Alignment.Center
            ) {
                Text(topic.iconEmoji, fontSize = 30.sp)
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 14.dp)
            ) {
                Text(
                    topic.nameEs,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    topic.descriptionEs,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.92f),
                    modifier = Modifier.padding(top = 3.dp)
                )
            }
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.28f)),
                contentAlignment = Alignment.Center
            ) {
                Text("▶", color = Color.White, fontSize = 14.sp, textAlign = TextAlign.Center)
            }
        }
    }
}
