package com.kidslab.physicslab.domain.engine

import kotlin.math.roundToInt

/**
 * Motor de movimiento uniforme (velocidad constante, sin aceleración).
 * Se usa en el laboratorio de Movimiento y en el de Velocidad.
 *
 * Fórmula: distancia = velocidad * tiempo
 */
object MovementEngine {

    data class MovementResult(
        val initialVelocityMs: Double,
        val timeS: Double,
        val distanceM: Double
    )

    fun simulate(initialVelocityMs: Double, timeS: Double): MovementResult {
        require(initialVelocityMs >= 0) { "La velocidad no puede ser negativa" }
        require(timeS >= 0) { "El tiempo no puede ser negativo" }
        val distance = initialVelocityMs * timeS
        return MovementResult(initialVelocityMs, timeS, distance)
    }

    /**
     * Devuelve la posición (0..1, fracción de la pista) del objeto en un instante
     * dado `elapsedS` dentro de una simulación total de `timeS`, útil para animar.
     */
    fun fractionAt(elapsedS: Double, timeS: Double): Float {
        if (timeS <= 0.0) return 1f
        return (elapsedS / timeS).coerceIn(0.0, 1.0).toFloat()
    }

    fun formattedDistance(distanceM: Double): String =
        "${(distanceM * 100).roundToInt() / 100.0} m"
}

/**
 * Motor de carrera de velocidades: compara dos objetos con velocidad constante
 * distinta durante el mismo tiempo, para que el niño vea quién llega más lejos.
 */
object SpeedRaceEngine {

    data class RacerResult(
        val label: String,
        val velocityMs: Double,
        val distanceM: Double
    )

    fun race(velocitiesMs: List<Pair<String, Double>>, timeS: Double): List<RacerResult> =
        velocitiesMs.map { (label, v) ->
            RacerResult(label, v, MovementEngine.simulate(v, timeS).distanceM)
        }.sortedByDescending { it.distanceM }
}
