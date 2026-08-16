package com.kidslab.physicslab.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kidslab.physicslab.ui.theme.BackgroundGradient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabScaffold(
    title: String,
    emoji: String,
    onBack: () -> Unit,
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 26.dp, bottomEnd = 26.dp))
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                TopAppBar(
                    title = {
                        Text("$emoji $title", fontSize = 20.sp, style = MaterialTheme.typography.titleLarge)
                    },
                    navigationIcon = { BouncyBackButton(onClick = onBack) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGradient)
        ) {
            content(Modifier.padding(padding))
        }
    }
}

@Composable
private fun BouncyBackButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(start = 8.dp)
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.22f))
            .bouncyClickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
    }
}
