package com.kidslab.physicslab.ui.navigation

object PhysicsLabRoutes {
    const val SCIENTIST_SETUP = "scientist_setup"
    const val LABS = "labs"
    const val NOTEBOOK = "notebook"

    const val LAB_MOVEMENT = "lab_movement"
    const val LAB_SPEED = "lab_speed"
    const val LAB_FORCE = "lab_force"
    const val LAB_MASS = "lab_mass"
    const val LAB_GRAVITY = "lab_gravity"
    const val LAB_FRICTION = "lab_friction"
    const val LAB_ENERGY = "lab_energy"
    const val LAB_MACHINES = "lab_machines"

    fun routeForTopic(topicId: String): String = when (topicId) {
        "movimiento" -> LAB_MOVEMENT
        "velocidad" -> LAB_SPEED
        "fuerza" -> LAB_FORCE
        "masa" -> LAB_MASS
        "gravedad" -> LAB_GRAVITY
        "friccion" -> LAB_FRICTION
        "energia" -> LAB_ENERGY
        "maquinas_simples" -> LAB_MACHINES
        else -> LABS
    }
}
