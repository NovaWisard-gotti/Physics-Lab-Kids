package com.kidslab.physicslab.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabScaffold(
    title: String,
    emoji: String,
    onBack: () -> Unit,
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("$emoji $title") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors()
            )
        }
    ) { padding ->
        content(Modifier.padding(padding))
    }
}
