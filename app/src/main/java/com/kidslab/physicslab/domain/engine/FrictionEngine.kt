package com.kidslab.physicslab.domain.engine

import com.kidslab.physicslab.domain.model.SurfaceType
import kotlin.math.roundToInt

/**
 * Motor de fricción: con el mismo impulso inicial (misma velocidad inicial),
 * cada superficie frena el objeto con una desaceleración distinta.
 *
 * Modelo simplificado: a_friccion = coeficiente * g (g = 9.8 m/s²)
 * Distancia de frenado: d = v² / (2 * a_friccion)
 */
object FrictionEngine {

    private const val GRAVITY = 9.8

    data class FrictionResult(
        val surface: SurfaceType,
        val initialVelocityMs: Double,
        val decelerationMs2: Double,
        val stoppingDistanceM: Double
    )

    fun simulate(surface: SurfaceType, initialVelocityMs: Double): FrictionResult {
        require(initialVelocityMs >= 0) { "La velocidad no puede ser negativa" }
        val deceleration = surface.frictionCoefficient * GRAVITY
        val distance = if (deceleration <= 0.0) Double.POSITIVE_INFINITY
        else (initialVelocityMs * initialVelocityMs) / (2 * deceleration)
        return FrictionResult(surface, initialVelocityMs, deceleration, distance)
    }

    fun simulateAll(initialVelocityMs: Double): List<FrictionResult> =
        SurfaceType.values().map { simulate(it, initialVelocityMs) }

    fun formattedDistance(distanceM: Double): String =
        "${(distanceM * 100).roundToInt() / 100.0} m"
}
