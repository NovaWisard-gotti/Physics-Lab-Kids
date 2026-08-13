package com.kidslab.physicslab.domain.engine

import com.kidslab.physicslab.domain.model.IntensityLevel
import kotlin.math.roundToInt

/**
 * Motor de fuerza y masa (segunda ley de Newton simplificada: a = F / m).
 * Se usa en el laboratorio "Fuerza y masa" y en el laboratorio dedicado a "Masa".
 *
 * Valores base para que los niveles bajo/medio/alto den resultados
 * intuitivos y consistentes:
 *   Fuerza base  = 10 N
 *   Masa base    = 5 kg
 */
object ForceMassEngine {

    private const val BASE_FORCE_N = 10.0
    private const val BASE_MASS_KG = 5.0

    data class ForceMassResult(
        val forceN: Double,
        val massKg: Double,
        val accelerationMs2: Double
    )

    fun forceNewtons(level: IntensityLevel): Double = BASE_FORCE_N * level.factor
    fun massKilograms(level: IntensityLevel): Double = BASE_MASS_KG * level.factor

    fun simulate(forceLevel: IntensityLevel, massLevel: IntensityLevel): ForceMassResult {
        val force = forceNewtons(forceLevel)
        val mass = massKilograms(massLevel)
        val acceleration = force / mass
        return ForceMassResult(force, mass, acceleration)
    }

    /**
     * Devuelve un valor 0..1 para animar qué tan rápido "arranca" el carrito,
     * normalizado contra el caso de mayor aceleración posible (fuerza alta, masa baja).
     */
    fun normalizedAcceleration(result: ForceMassResult): Float {
        val maxPossible = forceNewtons(IntensityLevel.HIGH) / massKilograms(IntensityLevel.LOW)
        return (result.accelerationMs2 / maxPossible).coerceIn(0.0, 1.0).toFloat()
    }

    fun formattedAcceleration(accelerationMs2: Double): String =
        "${(accelerationMs2 * 100).roundToInt() / 100.0} m/s²"
}
