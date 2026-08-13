package com.kidslab.physicslab.domain.model

/**
 * Unidades usadas en toda la app. Se mantienen simples y consistentes
 * para que un niño de 8 a 12 años pueda relacionarlas con el mundo real.
 *
 * distancia -> metros (m)
 * tiempo    -> segundos (s)
 * velocidad -> metros por segundo (m/s)
 * fuerza    -> newtons (N)
 * masa      -> kilogramos (kg)
 */
enum class PhysicsUnit(val symbol: String, val label: String) {
    METERS("m", "metros"),
    SECONDS("s", "segundos"),
    METERS_PER_SECOND("m/s", "metros por segundo"),
    NEWTONS("N", "newtons"),
    KILOGRAMS("kg", "kilogramos"),
    NONE("", "")
}

/**
 * Niveles cualitativos que el niño elige con botones (bajo/medio/alto),
 * cada uno se traduce internamente a un valor numérico consistente.
 */
enum class IntensityLevel(val displayEs: String, val factor: Double) {
    LOW("Baja", 1.0),
    MEDIUM("Media", 2.0),
    HIGH("Alta", 3.5)
}

enum class SurfaceType(val displayEs: String, val frictionCoefficient: Double, val emoji: String) {
    ICE("Hielo", 0.05, "🧊"),
    WOOD("Madera", 0.30, "🪵"),
    CARPET("Alfombra", 0.65, "🧶")
}

enum class PlanetMode(val displayEs: String, val gravity: Double, val emoji: String) {
    EARTH("Tierra", 9.8, "🌍"),
    MOON("Luna", 1.6, "🌕")
}

enum class SimpleMachineType(val displayEs: String) {
    LEVER("Palanca"),
    PULLEY("Polea"),
    INCLINED_PLANE("Plano inclinado")
}
