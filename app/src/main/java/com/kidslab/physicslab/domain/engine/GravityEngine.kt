package com.kidslab.physicslab.domain.engine

import com.kidslab.physicslab.domain.model.PlanetMode
import kotlin.math.roundToInt
import kotlin.math.sqrt

/**
 * Motor de gravedad: caída libre ideal (sin resistencia del aire).
 *
 * Concepto clave a mostrar: en una simulación ideal, la masa del objeto
 * NO afecta el tiempo de caída. Solo cambia si cambiamos de planeta (gravedad).
 *
 * Fórmula: t = raíz(2 * altura / g)
 */
object GravityEngine {

    data class FallResult(
        val objectLabel: String,
        val massKg: Double,
        val heightM: Double,
        val planet: PlanetMode,
        val fallTimeS: Double
    )

    fun simulateFall(objectLabel: String, massKg: Double, heightM: Double, planet: PlanetMode): FallResult {
        require(heightM >= 0) { "La altura no puede ser negativa" }
        val time = sqrt(2 * heightM / planet.gravity)
        return FallResult(objectLabel, massKg, heightM, planet, time)
    }

    /** Compara dos objetos de masas distintas, misma altura y mismo planeta. */
    fun compareTwoObjects(
        heightM: Double,
        planet: PlanetMode,
        objectA: Pair<String, Double>,
        objectB: Pair<String, Double>
    ): Pair<FallResult, FallResult> {
        val resultA = simulateFall(objectA.first, objectA.second, heightM, planet)
        val resultB = simulateFall(objectB.first, objectB.second, heightM, planet)
        return resultA to resultB
    }

    fun formattedTime(timeS: Double): String =
        "${(timeS * 100).roundToInt() / 100.0} s"
}
