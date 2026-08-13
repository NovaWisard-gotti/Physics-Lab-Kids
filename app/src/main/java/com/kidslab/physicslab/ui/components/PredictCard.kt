package com.kidslab.physicslab.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PredictCard(
    questionEs: String,
    options: List<String>,
    onOptionChosen: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth(), colors = CardDefaults.cardColors()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("Antes de experimentar…", style = MaterialTheme.typography.titleLarge)
            Text(questionEs, style = MaterialTheme.typography.bodyLarge)
            options.forEachIndexed { index, option ->
                if (index == 0) {
                    Button(onClick = { onOptionChosen(option) }, modifier = Modifier.fillMaxWidth()) {
                        Text(option)
                    }
                } else {
                    OutlinedButton(onClick = { onOptionChosen(option) }, modifier = Modifier.fillMaxWidth()) {
                        Text(option)
                    }
                }
            }
        }
    }
}
