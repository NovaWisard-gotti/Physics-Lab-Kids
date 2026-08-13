package com.kidslab.physicslab.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kidslab.physicslab.data.repository.PhysicsLabRepository
import com.kidslab.physicslab.ui.labs.LabListScreen
import com.kidslab.physicslab.ui.labs.energy.EnergyLabScreen
import com.kidslab.physicslab.ui.labs.forcemass.ForceLabScreen
import com.kidslab.physicslab.ui.labs.friction.FrictionLabScreen
import com.kidslab.physicslab.ui.labs.gravity.GravityLabScreen
import com.kidslab.physicslab.ui.labs.machines.SimpleMachinesLabScreen
import com.kidslab.physicslab.ui.labs.mass.MassLabScreen
import com.kidslab.physicslab.ui.labs.movement.MovementLabScreen
import com.kidslab.physicslab.ui.labs.speed.SpeedLabScreen
import com.kidslab.physicslab.ui.notebook.NotebookScreen
import com.kidslab.physicslab.ui.scientist.ScientistSetupScreen

@Composable
fun PhysicsLabNavHost(repository: PhysicsLabRepository) {
    val navController = rememberNavController()
    val startDestination = PhysicsLabRoutes.SCIENTIST_SETUP

    NavHost(navController = navController, startDestination = startDestination) {
        composable(PhysicsLabRoutes.SCIENTIST_SETUP) {
            ScientistSetupScreen(
                repository = repository,
                onDone = {
                    navController.navigate(PhysicsLabRoutes.LABS) {
                        popUpTo(PhysicsLabRoutes.SCIENTIST_SETUP) { inclusive = true }
                    }
                }
            )
        }
        composable(PhysicsLabRoutes.LABS) {
            LabListScreen(
                repository = repository,
                onOpenTopic = { route -> navController.navigate(route) },
                onOpenNotebook = { navController.navigate(PhysicsLabRoutes.NOTEBOOK) }
            )
        }
        composable(PhysicsLabRoutes.NOTEBOOK) {
            NotebookScreen(repository = repository, onBack = { navController.popBackStack() })
        }
        composable(PhysicsLabRoutes.LAB_MOVEMENT) {
            MovementLabScreen(repository = repository, onBack = { navController.popBackStack() })
        }
        composable(PhysicsLabRoutes.LAB_SPEED) {
            SpeedLabScreen(repository = repository, onBack = { navController.popBackStack() })
        }
        composable(PhysicsLabRoutes.LAB_FORCE) {
            ForceLabScreen(repository = repository, onBack = { navController.popBackStack() })
        }
        composable(PhysicsLabRoutes.LAB_MASS) {
            MassLabScreen(repository = repository, onBack = { navController.popBackStack() })
        }
        composable(PhysicsLabRoutes.LAB_GRAVITY) {
            GravityLabScreen(repository = repository, onBack = { navController.popBackStack() })
        }
        composable(PhysicsLabRoutes.LAB_FRICTION) {
            FrictionLabScreen(repository = repository, onBack = { navController.popBackStack() })
        }
        composable(PhysicsLabRoutes.LAB_ENERGY) {
            EnergyLabScreen(repository = repository, onBack = { navController.popBackStack() })
        }
        composable(PhysicsLabRoutes.LAB_MACHINES) {
            SimpleMachinesLabScreen(repository = repository, onBack = { navController.popBackStack() })
        }
    }
}
