package com.kidslab.physicslab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kidslab.physicslab.ui.navigation.PhysicsLabNavHost
import com.kidslab.physicslab.ui.theme.FisicaLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val container = (application as PhysicsLabApplication).container
        setContent {
            FisicaLabTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PhysicsLabRoot(container = container)
                }
            }
        }
    }
}

@Composable
private fun PhysicsLabRoot(container: com.kidslab.physicslab.di.AppContainer) {
    PhysicsLabNavHost(repository = container.repository)
}
