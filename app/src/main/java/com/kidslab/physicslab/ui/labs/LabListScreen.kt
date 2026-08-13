package com.kidslab.physicslab.ui.labs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kidslab.physicslab.data.local.entity.PhysicsTopicEntity
import com.kidslab.physicslab.data.repository.PhysicsLabRepository
import com.kidslab.physicslab.ui.navigation.PhysicsLabRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabListScreen(
    repository: PhysicsLabRepository,
    onOpenTopic: (String) -> Unit,
    onOpenNotebook: () -> Unit
) {
    val topics by repository.observeTopics().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Laboratorios de Física") },
                actions = {
                    IconButton(onClick = onOpenNotebook) {
                        Icon(androidx.compose.material.icons.Icons.Filled.Star, contentDescription = "Cuaderno")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                Text(
                    "Elige un laboratorio y descubre cómo funciona el mundo",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            items(topics, key = { it.id }) { topic ->
                TopicCard(topic = topic, onClick = { onOpenTopic(PhysicsLabRoutes.routeForTopic(topic.id)) })
            }
        }
    }
}

@Composable
private fun TopicCard(topic: PhysicsTopicEntity, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box {
                Text(topic.iconEmoji, fontSize = 30.sp)
            }
            Text(topic.nameEs, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 6.dp))
            Text(topic.descriptionEs, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 4.dp))
        }
    }
}
