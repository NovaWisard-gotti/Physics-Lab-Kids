package com.kidslab.physicslab.domain.engine

import kotlin.math.roundToInt
import kotlin.math.sqrt

/**
 * Motor de energía para el laboratorio de la montaña rusa.
 *
 * Conservación de energía (sin fricción, modelo ideal educativo):
 *   Energía total = Energía potencial (altura) + Energía cinética (movimiento)
 *   EP = m * g * h
 *   EC = 1/2 * m * v²
 *
 * Se normaliza a "porcentaje de la energía total" para animar barras fáciles
 * de entender, sin obligar al niño a manejar joules todavía.
 */
object EnergyEngine {

    private const val GRAVITY = 9.8

    data class EnergyPoint(
        val heightM: Double,
        val potentialEnergyJ: Double,
        val kineticEnergyJ: Double,
        val speedMs: Double,
        val potentialFraction: Float,
        val kineticFraction: Float
    )

    /**
     * Calcula la energía en un punto de la pista con altura `heightM`,
     * dado que el carrito partió del reposo desde `startHeightM` (energía total fija).
     */
    fun energyAt(massKg: Double, startHeightM: Double, heightM: Double): EnergyPoint {
        require(startHeightM >= 0 && heightM >= 0) { "Las alturas no pueden ser negativas" }
        val clampedHeight = heightM.coerceIn(0.0, startHeightM)
        val totalEnergy = massKg * GRAVITY * startHeightM
        val potential = massKg * GRAVITY * clampedHeight
        val kinetic = (totalEnergy - potential).coerceAtLeast(0.0)
        val speed = sqrt((2 * kinetic / massKg).coerceAtLeast(0.0))
        val potentialFraction = if (totalEnergy <= 0.0) 0f else (potential / totalEnergy).toFloat()
        val kineticFraction = if (totalEnergy <= 0.0) 0f else (kinetic / totalEnergy).toFloat()
        return EnergyPoint(clampedHeight, potential, kinetic, speed, potentialFraction, kineticFraction)
    }

    /** Genera una curva de puntos (para animar el recorrido de la montaña rusa). */
    fun trackCurve(massKg: Double, startHeightM: Double, steps: Int = 20): List<EnergyPoint> {
        val safeSteps = steps.coerceAtLeast(2)
        return (0..safeSteps).map { i ->
            // La altura baja y sube como una montaña rusa simple (valle en el medio).
            val progress = i.toDouble() / safeSteps
            val height = startHeightM * kotlin.math.abs(kotlin.math.cos(progress * Math.PI))
            energyAt(massKg, startHeightM, height)
        }
    }

    fun formattedSpeed(speedMs: Double): String =
        "${(speedMs * 100).roundToInt() / 100.0} m/s"
}
