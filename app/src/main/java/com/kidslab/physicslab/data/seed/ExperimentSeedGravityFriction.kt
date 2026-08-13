package com.kidslab.physicslab.data.seed

import com.kidslab.physicslab.data.local.entity.ExperimentEntity
import com.kidslab.physicslab.data.local.entity.ExperimentParameterEntity

/** Experimentos de los temas Gravedad y Fricción (6 experimentos). */
object ExperimentSeedGravityFriction {

    val experiments = listOf(
        ExperimentEntity(
            id = "gra_01", topicId = TopicSeed.GRAVEDAD,
            titleEs = "Misma altura, distinta masa",
            instructionEs = "Deja caer una pelota liviana y una pelota pesada desde la misma altura, en la Tierra.",
            predictQuestionEs = "¿Cuál pelota crees que llegará primero al suelo: la liviana o la pesada?",
            explanationEs = "En una caída ideal, sin aire, ambas pelotas llegan al mismo tiempo. La masa no cambia el tiempo de caída, ¡solo la gravedad del lugar!",
            difficulty = 2, orderIndex = 1
        ),
        ExperimentEntity(
            id = "gra_02", topicId = TopicSeed.GRAVEDAD,
            titleEs = "Distinta altura",
            instructionEs = "Deja caer el mismo objeto desde distintas alturas y compara el tiempo de caída.",
            predictQuestionEs = "Si sueltas el objeto desde más alto, ¿tardará más o menos en llegar al suelo?",
            explanationEs = "Desde más alto, el objeto tarda más tiempo en caer, porque tiene que recorrer más distancia antes de tocar el suelo.",
            difficulty = 1, orderIndex = 2
        ),
        ExperimentEntity(
            id = "gra_03", topicId = TopicSeed.GRAVEDAD,
            titleEs = "Tierra vs. Luna",
            instructionEs = "Deja caer el mismo objeto desde la misma altura, primero en la Tierra y luego en la Luna.",
            predictQuestionEs = "¿Dónde crees que un objeto tarda más en caer: en la Tierra o en la Luna?",
            explanationEs = "En la Luna la gravedad es mucho más débil que en la Tierra, así que el objeto cae mucho más despacio y tarda más tiempo.",
            difficulty = 2, orderIndex = 3
        ),
        ExperimentEntity(
            id = "fri_01", topicId = TopicSeed.FRICCION,
            titleEs = "Hielo vs. madera",
            instructionEs = "Empuja el mismo objeto con el mismo impulso sobre hielo y sobre madera.",
            predictQuestionEs = "¿En qué superficie crees que el objeto se deslizará más lejos: hielo o madera?",
            explanationEs = "El hielo tiene muy poca fricción, así que frena poco al objeto y este se desliza mucho más lejos que sobre madera.",
            difficulty = 1, orderIndex = 1
        ),
        ExperimentEntity(
            id = "fri_02", topicId = TopicSeed.FRICCION,
            titleEs = "Madera vs. alfombra",
            instructionEs = "Ahora compara el mismo impulso sobre madera y sobre alfombra.",
            predictQuestionEs = "¿Qué superficie frenará más rápido al objeto: la madera o la alfombra?",
            explanationEs = "La alfombra tiene más fricción que la madera, así que frena al objeto mucho más rápido y este recorre menos distancia.",
            difficulty = 1, orderIndex = 2
        ),
        ExperimentEntity(
            id = "fri_03", topicId = TopicSeed.FRICCION,
            titleEs = "Las tres superficies",
            instructionEs = "Compara hielo, madera y alfombra al mismo tiempo, con el mismo impulso inicial.",
            predictQuestionEs = "Ordena de mayor a menor: ¿en qué superficie crees que el objeto llegará más lejos?",
            explanationEs = "De mayor a menor distancia: hielo, madera y alfombra. A mayor fricción, menor distancia recorrida.",
            difficulty = 2, orderIndex = 3
        )
    )

    val parameters = listOf(
        ExperimentParameterEntity(experimentId = "gra_01", paramKey = "height", labelEs = "Altura", unitSymbol = "m", minValue = 1.0, maxValue = 10.0, defaultValue = 5.0),
        ExperimentParameterEntity(experimentId = "gra_01", paramKey = "massA", labelEs = "Masa pelota liviana", unitSymbol = "kg", minValue = 0.1, maxValue = 1.0, defaultValue = 0.2),
        ExperimentParameterEntity(experimentId = "gra_01", paramKey = "massB", labelEs = "Masa pelota pesada", unitSymbol = "kg", minValue = 1.0, maxValue = 5.0, defaultValue = 3.0),

        ExperimentParameterEntity(experimentId = "gra_02", paramKey = "height", labelEs = "Altura", unitSymbol = "m", minValue = 1.0, maxValue = 15.0, defaultValue = 4.0),
        ExperimentParameterEntity(experimentId = "gra_02", paramKey = "mass", labelEs = "Masa (fija)", unitSymbol = "kg", minValue = 1.0, maxValue = 1.0, defaultValue = 1.0),

        ExperimentParameterEntity(experimentId = "gra_03", paramKey = "height", labelEs = "Altura", unitSymbol = "m", minValue = 1.0, maxValue = 10.0, defaultValue = 5.0),
        ExperimentParameterEntity(experimentId = "gra_03", paramKey = "mass", labelEs = "Masa (fija)", unitSymbol = "kg", minValue = 1.0, maxValue = 1.0, defaultValue = 1.0),

        ExperimentParameterEntity(experimentId = "fri_01", paramKey = "initialVelocity", labelEs = "Impulso inicial", unitSymbol = "m/s", minValue = 1.0, maxValue = 8.0, defaultValue = 4.0),

        ExperimentParameterEntity(experimentId = "fri_02", paramKey = "initialVelocity", labelEs = "Impulso inicial", unitSymbol = "m/s", minValue = 1.0, maxValue = 8.0, defaultValue = 4.0),

        ExperimentParameterEntity(experimentId = "fri_03", paramKey = "initialVelocity", labelEs = "Impulso inicial", unitSymbol = "m/s", minValue = 1.0, maxValue = 8.0, defaultValue = 5.0)
    )
}
