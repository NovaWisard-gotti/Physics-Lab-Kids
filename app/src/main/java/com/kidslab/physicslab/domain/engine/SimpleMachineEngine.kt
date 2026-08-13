package com.kidslab.physicslab.domain.engine

import kotlin.math.roundToInt

/**
 * Motor de máquinas simples: calcula cuánta fuerza necesitamos con cada
 * máquina para mover una carga, usando la idea de "ventaja mecánica"
 * (cuántas veces la máquina multiplica nuestra fuerza).
 */
object SimpleMachineEngine {

    data class MachineResult(
        val mechanicalAdvantage: Double,
        val requiredForceN: Double
    )

    /**
     * Palanca: ventaja mecánica = brazo de esfuerzo / brazo de carga.
     */
    fun lever(loadWeightN: Double, effortArmM: Double, loadArmM: Double): MachineResult {
        require(effortArmM > 0 && loadArmM > 0) { "Los brazos deben ser mayores que cero" }
        val advantage = effortArmM / loadArmM
        val force = loadWeightN / advantage
        return MachineResult(advantage, force)
    }

    /**
     * Polea: fija = 1 (solo cambia dirección), móvil simple = 2 (reduce la fuerza a la mitad).
     */
    fun pulley(loadWeightN: Double, supportingRopes: Int): MachineResult {
        require(supportingRopes >= 1) { "Debe haber al menos una cuerda" }
        val advantage = supportingRopes.toDouble()
        val force = loadWeightN / advantage
        return MachineResult(advantage, force)
    }

    /**
     * Plano inclinado: ventaja mecánica = longitud de la rampa / altura.
     */
    fun inclinedPlane(loadWeightN: Double, rampLengthM: Double, heightM: Double): MachineResult {
        require(rampLengthM > 0 && heightM > 0) { "La longitud y la altura deben ser mayores que cero" }
        val advantage = rampLengthM / heightM
        val force = loadWeightN / advantage
        return MachineResult(advantage, force)
    }

    fun formattedForce(forceN: Double): String =
        "${(forceN * 100).roundToInt() / 100.0} N"

    fun formattedAdvantage(advantage: Double): String =
        "${(advantage * 100).roundToInt() / 100.0}x"
}
