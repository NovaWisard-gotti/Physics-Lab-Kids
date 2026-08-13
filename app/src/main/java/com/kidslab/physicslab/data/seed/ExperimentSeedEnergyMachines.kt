package com.kidslab.physicslab.data.seed

import com.kidslab.physicslab.data.local.entity.ExperimentEntity
import com.kidslab.physicslab.data.local.entity.ExperimentParameterEntity

/** Experimentos de los temas Energía y Máquinas simples (6 experimentos). */
object ExperimentSeedEnergyMachines {

    val experiments = listOf(
        ExperimentEntity(
            id = "ene_01", topicId = TopicSeed.ENERGIA,
            titleEs = "Montaña baja",
            instructionEs = "Suelta el carrito desde una colina baja y observa las barras de energía potencial y cinética.",
            predictQuestionEs = "Cuando el carrito está arriba de todo, ¿tiene más energía de movimiento o de altura?",
            explanationEs = "Arriba, toda la energía es de altura (potencial). Al bajar, esa energía se transforma en energía de movimiento (cinética).",
            difficulty = 1, orderIndex = 1
        ),
        ExperimentEntity(
            id = "ene_02", topicId = TopicSeed.ENERGIA,
            titleEs = "Montaña media",
            instructionEs = "Ahora prueba con una colina más alta y observa cómo cambian las barras al bajar.",
            predictQuestionEs = "Si la colina es más alta, ¿el carrito llegará abajo más rápido o más lento que antes?",
            explanationEs = "Con más altura hay más energía potencial disponible, así que se convierte en más energía cinética y el carrito llega más rápido.",
            difficulty = 2, orderIndex = 2
        ),
        ExperimentEntity(
            id = "ene_03", topicId = TopicSeed.ENERGIA,
            titleEs = "Montaña alta",
            instructionEs = "Prueba la colina más alta de todas y observa el punto donde las barras se cruzan.",
            predictQuestionEs = "¿En qué momento crees que la energía de altura y la de movimiento son exactamente iguales?",
            explanationEs = "A la mitad de la bajada, la energía potencial y la cinética suelen ser aproximadamente iguales: la energía total no cambia, solo se transforma.",
            difficulty = 3, orderIndex = 3
        ),
        ExperimentEntity(
            id = "maq_01", topicId = TopicSeed.MAQUINAS,
            titleEs = "La palanca",
            instructionEs = "Mueve el punto de apoyo de la palanca y observa cuánta fuerza necesitas para levantar la carga.",
            predictQuestionEs = "Si alejas tu mano del punto de apoyo, ¿necesitarás más fuerza o menos fuerza para levantar la carga?",
            explanationEs = "Cuanto más lejos empujas del punto de apoyo, menos fuerza necesitas: la palanca multiplica tu esfuerzo.",
            difficulty = 2, orderIndex = 1
        ),
        ExperimentEntity(
            id = "maq_02", topicId = TopicSeed.MAQUINAS,
            titleEs = "La polea",
            instructionEs = "Agrega más cuerdas de apoyo a la polea y observa cuánta fuerza necesitas para subir la carga.",
            predictQuestionEs = "Si usas dos cuerdas en vez de una, ¿necesitarás la mitad de fuerza o el doble de fuerza?",
            explanationEs = "Con dos cuerdas de apoyo, la carga se reparte entre ambas: necesitas aproximadamente la mitad de la fuerza.",
            difficulty = 2, orderIndex = 2
        ),
        ExperimentEntity(
            id = "maq_03", topicId = TopicSeed.MAQUINAS,
            titleEs = "El plano inclinado",
            instructionEs = "Cambia el largo de la rampa y observa cuánta fuerza necesitas para subir la carga hasta la misma altura.",
            predictQuestionEs = "Si la rampa es más larga (pero llega a la misma altura), ¿necesitarás más fuerza o menos fuerza?",
            explanationEs = "Una rampa más larga para la misma altura requiere menos fuerza, aunque tengas que empujar por más distancia.",
            difficulty = 2, orderIndex = 3
        )
    )

    val parameters = listOf(
        ExperimentParameterEntity(experimentId = "ene_01", paramKey = "startHeight", labelEs = "Altura de la colina", unitSymbol = "m", minValue = 1.0, maxValue = 3.0, defaultValue = 2.0),
        ExperimentParameterEntity(experimentId = "ene_01", paramKey = "mass", labelEs = "Masa del carrito", unitSymbol = "kg", minValue = 1.0, maxValue = 5.0, defaultValue = 2.0),

        ExperimentParameterEntity(experimentId = "ene_02", paramKey = "startHeight", labelEs = "Altura de la colina", unitSymbol = "m", minValue = 3.0, maxValue = 6.0, defaultValue = 5.0),
        ExperimentParameterEntity(experimentId = "ene_02", paramKey = "mass", labelEs = "Masa del carrito", unitSymbol = "kg", minValue = 1.0, maxValue = 5.0, defaultValue = 2.0),

        ExperimentParameterEntity(experimentId = "ene_03", paramKey = "startHeight", labelEs = "Altura de la colina", unitSymbol = "m", minValue = 6.0, maxValue = 10.0, defaultValue = 8.0),
        ExperimentParameterEntity(experimentId = "ene_03", paramKey = "mass", labelEs = "Masa del carrito", unitSymbol = "kg", minValue = 1.0, maxValue = 5.0, defaultValue = 2.0),

        ExperimentParameterEntity(experimentId = "maq_01", paramKey = "effortArm", labelEs = "Brazo de esfuerzo", unitSymbol = "m", minValue = 0.5, maxValue = 3.0, defaultValue = 1.5),
        ExperimentParameterEntity(experimentId = "maq_01", paramKey = "loadArm", labelEs = "Brazo de carga (fijo)", unitSymbol = "m", minValue = 0.5, maxValue = 0.5, defaultValue = 0.5),
        ExperimentParameterEntity(experimentId = "maq_01", paramKey = "loadWeight", labelEs = "Peso de la carga", unitSymbol = "N", minValue = 20.0, maxValue = 20.0, defaultValue = 20.0),

        ExperimentParameterEntity(experimentId = "maq_02", paramKey = "ropes", labelEs = "Cuerdas de apoyo", unitSymbol = "", minValue = 1.0, maxValue = 3.0, defaultValue = 1.0),
        ExperimentParameterEntity(experimentId = "maq_02", paramKey = "loadWeight", labelEs = "Peso de la carga", unitSymbol = "N", minValue = 20.0, maxValue = 20.0, defaultValue = 20.0),

        ExperimentParameterEntity(experimentId = "maq_03", paramKey = "rampLength", labelEs = "Largo de la rampa", unitSymbol = "m", minValue = 2.0, maxValue = 6.0, defaultValue = 3.0),
        ExperimentParameterEntity(experimentId = "maq_03", paramKey = "height", labelEs = "Altura (fija)", unitSymbol = "m", minValue = 1.0, maxValue = 1.0, defaultValue = 1.0),
        ExperimentParameterEntity(experimentId = "maq_03", paramKey = "loadWeight", labelEs = "Peso de la carga", unitSymbol = "N", minValue = 20.0, maxValue = 20.0, defaultValue = 20.0)
    )
}
