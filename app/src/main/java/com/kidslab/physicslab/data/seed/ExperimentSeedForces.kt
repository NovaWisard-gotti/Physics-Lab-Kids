package com.kidslab.physicslab.data.seed

import com.kidslab.physicslab.data.local.entity.ExperimentEntity
import com.kidslab.physicslab.data.local.entity.ExperimentParameterEntity

/** Experimentos de los temas Fuerza y Masa (6 experimentos). */
object ExperimentSeedForces {

    val experiments = listOf(
        ExperimentEntity(
            id = "fue_01", topicId = TopicSeed.FUERZA,
            titleEs = "Empuje bajo vs. empuje alto",
            instructionEs = "Con la misma masa, cambia la fuerza de empuje entre baja, media y alta.",
            predictQuestionEs = "Si empujas más fuerte con el mismo carrito, ¿arrancará más rápido o más lento?",
            explanationEs = "Con la misma masa, más fuerza produce más aceleración: el carrito arranca más rápido.",
            difficulty = 1, orderIndex = 1
        ),
        ExperimentEntity(
            id = "fue_02", topicId = TopicSeed.FUERZA,
            titleEs = "Fuerza y aceleración",
            instructionEs = "Prueba las tres intensidades de fuerza y compara la aceleración resultante en el carrito.",
            predictQuestionEs = "¿Crees que la aceleración crece en la misma proporción que la fuerza?",
            explanationEs = "Sí: si la masa no cambia, la aceleración es proporcional a la fuerza aplicada (a = F ÷ m).",
            difficulty = 2, orderIndex = 2
        ),
        ExperimentEntity(
            id = "fue_03", topicId = TopicSeed.FUERZA,
            titleEs = "Empujar objetos con distinta fuerza",
            instructionEs = "Compara el resultado de empujar con fuerza baja y con fuerza alta, manteniendo la masa fija en nivel medio.",
            predictQuestionEs = "¿Qué carrito crees que se moverá con más aceleración: el empujado con fuerza baja o con fuerza alta?",
            explanationEs = "El carrito empujado con más fuerza acelera más, porque la fuerza es la causa directa del cambio de velocidad.",
            difficulty = 1, orderIndex = 3
        ),
        ExperimentEntity(
            id = "masa_01", topicId = TopicSeed.MASA,
            titleEs = "Carrito liviano vs. carrito pesado",
            instructionEs = "Con la misma fuerza de empuje, cambia la masa del carrito entre baja, media y alta.",
            predictQuestionEs = "Si empujas igual de fuerte, ¿el carrito pesado acelerará más o menos que el liviano?",
            explanationEs = "Con la misma fuerza, a mayor masa, menor aceleración: cuesta más mover algo pesado.",
            difficulty = 1, orderIndex = 1
        ),
        ExperimentEntity(
            id = "masa_02", topicId = TopicSeed.MASA,
            titleEs = "Masa y aceleración",
            instructionEs = "Explora las tres intensidades de masa y observa cómo cambia la aceleración con fuerza fija.",
            predictQuestionEs = "¿Qué carrito acelerará menos: el de masa baja o el de masa alta?",
            explanationEs = "El carrito de masa alta acelera menos, porque la misma fuerza tiene que mover más 'cantidad de materia'.",
            difficulty = 2, orderIndex = 2
        ),
        ExperimentEntity(
            id = "masa_03", topicId = TopicSeed.MASA,
            titleEs = "¿Qué cuesta más empujar?",
            instructionEs = "Compara un carrito de masa baja contra uno de masa alta, con la misma fuerza media.",
            predictQuestionEs = "Imagina empujar un carrito de juguete vacío y uno lleno de libros con la misma fuerza. ¿Cuál se mueve más rápido?",
            explanationEs = "El carrito vacío (menos masa) se mueve más rápido, porque necesita menos fuerza para acelerar la misma cantidad.",
            difficulty = 1, orderIndex = 3
        )
    )

    val parameters = listOf(
        ExperimentParameterEntity(experimentId = "fue_01", paramKey = "forceLevel", labelEs = "Nivel de fuerza", unitSymbol = "N", minValue = 1.0, maxValue = 3.0, defaultValue = 1.0),
        ExperimentParameterEntity(experimentId = "fue_01", paramKey = "massLevel", labelEs = "Nivel de masa (fijo)", unitSymbol = "kg", minValue = 2.0, maxValue = 2.0, defaultValue = 2.0),

        ExperimentParameterEntity(experimentId = "fue_02", paramKey = "forceLevel", labelEs = "Nivel de fuerza", unitSymbol = "N", minValue = 1.0, maxValue = 3.0, defaultValue = 2.0),
        ExperimentParameterEntity(experimentId = "fue_02", paramKey = "massLevel", labelEs = "Nivel de masa (fijo)", unitSymbol = "kg", minValue = 2.0, maxValue = 2.0, defaultValue = 2.0),

        ExperimentParameterEntity(experimentId = "fue_03", paramKey = "forceLevel", labelEs = "Nivel de fuerza", unitSymbol = "N", minValue = 1.0, maxValue = 3.0, defaultValue = 3.0),
        ExperimentParameterEntity(experimentId = "fue_03", paramKey = "massLevel", labelEs = "Nivel de masa (fijo)", unitSymbol = "kg", minValue = 2.0, maxValue = 2.0, defaultValue = 2.0),

        ExperimentParameterEntity(experimentId = "masa_01", paramKey = "massLevel", labelEs = "Nivel de masa", unitSymbol = "kg", minValue = 1.0, maxValue = 3.0, defaultValue = 1.0),
        ExperimentParameterEntity(experimentId = "masa_01", paramKey = "forceLevel", labelEs = "Nivel de fuerza (fijo)", unitSymbol = "N", minValue = 2.0, maxValue = 2.0, defaultValue = 2.0),

        ExperimentParameterEntity(experimentId = "masa_02", paramKey = "massLevel", labelEs = "Nivel de masa", unitSymbol = "kg", minValue = 1.0, maxValue = 3.0, defaultValue = 2.0),
        ExperimentParameterEntity(experimentId = "masa_02", paramKey = "forceLevel", labelEs = "Nivel de fuerza (fijo)", unitSymbol = "N", minValue = 2.0, maxValue = 2.0, defaultValue = 2.0),

        ExperimentParameterEntity(experimentId = "masa_03", paramKey = "massLevel", labelEs = "Nivel de masa", unitSymbol = "kg", minValue = 1.0, maxValue = 3.0, defaultValue = 3.0),
        ExperimentParameterEntity(experimentId = "masa_03", paramKey = "forceLevel", labelEs = "Nivel de fuerza (fijo)", unitSymbol = "N", minValue = 2.0, maxValue = 2.0, defaultValue = 2.0)
    )
}
