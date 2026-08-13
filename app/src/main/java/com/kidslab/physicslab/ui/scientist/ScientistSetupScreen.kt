package com.kidslab.physicslab.ui.scientist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kidslab.physicslab.data.repository.PhysicsLabRepository

@Composable
fun ScientistSetupScreen(repository: PhysicsLabRepository, onDone: () -> Unit) {
    val viewModel: ScientistSetupViewModel = viewModel(factory = ScientistSetupViewModel.Factory(repository))
    val state by viewModel.uiState.collectAsState()

    if (state.saved) {
        onDone()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Crea tu científico junior", style = MaterialTheme.typography.headlineMedium)

        Box(
            modifier = Modifier
                .size(96.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .background(Color(android.graphics.Color.parseColor(state.coatColorHex))),
            contentAlignment = Alignment.Center
        ) {
            Text(state.avatarId, fontSize = 44.sp)
        }

        OutlinedTextField(
            value = state.name,
            onValueChange = viewModel::onNameChange,
            label = { Text("¿Cómo te llamas?") },
            placeholder = { Text("Escribe tu nombre") },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            modifier = Modifier.fillMaxWidth()
        )

        Text("Elige tu avatar", style = MaterialTheme.typography.titleLarge)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            AVATARS.forEach { avatar ->
                AvatarOption(
                    emoji = avatar,
                    selected = avatar == state.avatarId,
                    onClick = { viewModel.onAvatarChange(avatar) }
                )
            }
        }

        Text("Elige el color de tu bata", style = MaterialTheme.typography.titleLarge)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            COAT_COLORS.forEach { colorHex ->
                ColorOption(
                    colorHex = colorHex,
                    selected = colorHex == state.coatColorHex,
                    onClick = { viewModel.onCoatColorChange(colorHex) }
                )
            }
        }

        Button(
            onClick = viewModel::save,
            enabled = state.name.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("¡Empezar a experimentar!")
        }
    }
}

@Composable
private fun AvatarOption(emoji: String, selected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(56.dp),
        onClick = onClick,
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
        )
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(emoji, fontSize = 28.sp)
        }
    }
}

@Composable
private fun ColorOption(colorHex: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(Color(android.graphics.Color.parseColor(colorHex)))
            .then(
                if (selected) {
                    Modifier.border(3.dp, MaterialTheme.colorScheme.onBackground, CircleShape)
                } else Modifier
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Text("✓", color = Color.White, fontSize = 18.sp)
        }
    }
}
