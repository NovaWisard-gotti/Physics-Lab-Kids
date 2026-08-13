package com.kidslab.physicslab.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UnitChip(value: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.primary
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun CompareCard(
    predictedEs: String,
    resultDescriptionEs: String,
    matched: Boolean,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Tu predicción vs. lo que pasó", style = MaterialTheme.typography.titleLarge)
            Text("Predijiste: $predictedEs", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(top = 8.dp))
            Text("Resultado: $resultDescriptionEs", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(top = 4.dp))
            Text(
                text = if (matched) "¡Tu predicción fue correcta! \uD83C\uDF89" else "Esta vez el resultado fue distinto a tu predicción, ¡eso también es aprender!",
                style = MaterialTheme.typography.bodyLarge,
                color = if (matched) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun ExplainCard(explanationEs: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("¿Por qué pasó esto?", style = MaterialTheme.typography.titleLarge)
            Text(explanationEs, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Composable
fun ParameterSliderRow(
    labelEs: String,
    unitSymbol: String,
    value: Double,
    min: Double,
    max: Double,
    onValueChange: (Double) -> Unit,
    enabled: Boolean = true
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Row {
            Text("$labelEs: ", style = MaterialTheme.typography.bodyLarge)
            Text(
                text = "${String.format("%.1f", value)} $unitSymbol",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
        androidx.compose.material3.Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toDouble()) },
            valueRange = min.toFloat()..(if (max > min) max.toFloat() else (min + 1f)),
            enabled = enabled && max > min
        )
    }
}
