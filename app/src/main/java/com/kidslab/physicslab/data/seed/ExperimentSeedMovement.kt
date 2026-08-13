package com.kidslab.physicslab.data.seed

import com.kidslab.physicslab.data.local.entity.ExperimentEntity
import com.kidslab.physicslab.data.local.entity.ExperimentParameterEntity

/**
 * Experimentos de los temas Movimiento y Velocidad (6 experimentos).
 * El resto de temas están en ExperimentSeedForces, ExperimentSeedGravityFriction
 * y ExperimentSeedEnergyMachines para mantener cada archivo manejable.
 */
object ExperimentSeedMovement {

    val experiments = listOf(
        ExperimentEntity(
            id = "mov_01", topicId = TopicSeed.MOVIMIENTO,
            titleEs = "Pista corta",
            instructionEs = "Elige una velocidad inicial y un tiempo, y observa cuánto avanza el carrito por la pista.",
            predictQuestionEs = "Si el carrito va más rápido, ¿crees que recorrerá más distancia o menos distancia en el mismo tiempo?",
            explanationEs = "La distancia recorrida es el resultado de multiplicar la velocidad por el tiempo. A más velocidad, con el mismo tiempo, el carrito llega más lejos.",
            difficulty = 1, orderIndex = 1
        ),
        ExperimentEntity(
            id = "mov_02", topicId = TopicSeed.MOVIMIENTO,
            titleEs = "Pista larga",
            instructionEs = "Ahora prueba con tiempos más largos y velocidades más altas en una pista más grande.",
            predictQuestionEs = "Si duplicas el tiempo dejando la misma velocidad, ¿la distancia se duplica también?",
            explanationEs = "Sí: en movimiento uniforme, si el tiempo se duplica, la distancia recorrida también se duplica, porque distancia = velocidad × tiempo.",
            difficulty = 1, orderIndex = 2
        ),
        ExperimentEntity(
            id = "mov_03", topicId = TopicSeed.MOVIMIENTO,
            titleEs = "Mismo tiempo, distinta velocidad",
            instructionEs = "Deja el tiempo fijo y solo cambia la velocidad inicial. Observa cómo cambia la distancia final.",
            predictQuestionEs = "Con el mismo tiempo, ¿qué carrito llega más lejos: el lento o el rápido?",
            explanationEs = "Con el mismo tiempo, el carrito más rápido siempre recorre más distancia, porque la velocidad es lo único que cambió.",
            difficulty = 2, orderIndex = 3
        ),
        ExperimentEntity(
            id = "vel_01", topicId = TopicSeed.VELOCIDAD,
            titleEs = "Carrera de dos velocidades",
            instructionEs = "Dos carritos corren al mismo tiempo con velocidades distintas. ¿Cuál ganará la carrera?",
            predictQuestionEs = "¿Cuál de los dos carritos crees que va a ganar la carrera?",
            explanationEs = "El carrito con mayor velocidad recorre más distancia en el mismo tiempo, así que llega primero a la meta.",
            difficulty = 1, orderIndex = 1
        ),
        ExperimentEntity(
            id = "vel_02", topicId = TopicSeed.VELOCIDAD,
            titleEs = "Carrera de tres velocidades",
            instructionEs = "Ahora compite con tres carritos: uno lento, uno medio y uno rápido.",
            predictQuestionEs = "¿En qué orden crees que llegarán los tres carritos a la meta?",
            explanationEs = "El orden de llegada siempre coincide con el orden de las velocidades: el más rápido llega primero, el más lento llega último.",
            difficulty = 2, orderIndex = 2
        ),
        ExperimentEntity(
            id = "vel_03", topicId = TopicSeed.VELOCIDAD,
            titleEs = "Velocidad y distancia",
            instructionEs = "Cambia la velocidad de un solo carrito con un tiempo fijo de 5 segundos y observa la distancia final.",
            predictQuestionEs = "Si aumentas la velocidad al doble, ¿la distancia también aumenta al doble?",
            explanationEs = "Sí, porque el tiempo no cambió: si la velocidad se duplica, la distancia recorrida también se duplica.",
            difficulty = 2, orderIndex = 3
        )
    )

    val parameters = listOf(
        ExperimentParameterEntity(experimentId = "mov_01", paramKey = "initialVelocity", labelEs = "Velocidad inicial", unitSymbol = "m/s", minValue = 0.5, maxValue = 5.0, defaultValue = 2.0),
        ExperimentParameterEntity(experimentId = "mov_01", paramKey = "time", labelEs = "Tiempo", unitSymbol = "s", minValue = 1.0, maxValue = 5.0, defaultValue = 3.0),

        ExperimentParameterEntity(experimentId = "mov_02", paramKey = "initialVelocity", labelEs = "Velocidad inicial", unitSymbol = "m/s", minValue = 1.0, maxValue = 8.0, defaultValue = 4.0),
        ExperimentParameterEntity(experimentId = "mov_02", paramKey = "time", labelEs = "Tiempo", unitSymbol = "s", minValue = 2.0, maxValue = 10.0, defaultValue = 6.0),

        ExperimentParameterEntity(experimentId = "mov_03", paramKey = "initialVelocity", labelEs = "Velocidad inicial", unitSymbol = "m/s", minValue = 0.5, maxValue = 6.0, defaultValue = 3.0),
        ExperimentParameterEntity(experimentId = "mov_03", paramKey = "time", labelEs = "Tiempo (fijo)", unitSymbol = "s", minValue = 4.0, maxValue = 4.0, defaultValue = 4.0),

        ExperimentParameterEntity(experimentId = "vel_01", paramKey = "velocityA", labelEs = "Velocidad carrito A", unitSymbol = "m/s", minValue = 1.0, maxValue = 6.0, defaultValue = 2.0),
        ExperimentParameterEntity(experimentId = "vel_01", paramKey = "velocityB", labelEs = "Velocidad carrito B", unitSymbol = "m/s", minValue = 1.0, maxValue = 6.0, defaultValue = 4.0),
        ExperimentParameterEntity(experimentId = "vel_01", paramKey = "time", labelEs = "Tiempo de carrera", unitSymbol = "s", minValue = 1.0, maxValue = 6.0, defaultValue = 4.0),

        ExperimentParameterEntity(experimentId = "vel_02", paramKey = "velocityA", labelEs = "Velocidad carrito lento", unitSymbol = "m/s", minValue = 1.0, maxValue = 3.0, defaultValue = 1.5),
        ExperimentParameterEntity(experimentId = "vel_02", paramKey = "velocityB", labelEs = "Velocidad carrito medio", unitSymbol = "m/s", minValue = 3.0, maxValue = 5.0, defaultValue = 3.5),
        ExperimentParameterEntity(experimentId = "vel_02", paramKey = "velocityC", labelEs = "Velocidad carrito rápido", unitSymbol = "m/s", minValue = 5.0, maxValue = 8.0, defaultValue = 6.0),
        ExperimentParameterEntity(experimentId = "vel_02", paramKey = "time", labelEs = "Tiempo de carrera", unitSymbol = "s", minValue = 1.0, maxValue = 6.0, defaultValue = 4.0),

        ExperimentParameterEntity(experimentId = "vel_03", paramKey = "velocity", labelEs = "Velocidad", unitSymbol = "m/s", minValue = 1.0, maxValue = 10.0, defaultValue = 3.0),
        ExperimentParameterEntity(experimentId = "vel_03", paramKey = "time", labelEs = "Tiempo (fijo)", unitSymbol = "s", minValue = 5.0, maxValue = 5.0, defaultValue = 5.0)
    )
}
