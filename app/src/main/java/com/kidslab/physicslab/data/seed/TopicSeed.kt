package com.kidslab.physicslab.data.seed

import com.kidslab.physicslab.data.local.entity.BadgeEntity
import com.kidslab.physicslab.data.local.entity.PhysicsTopicEntity

/** Los 8 temas de física que ve el científico junior en la pantalla de Laboratorios. */
object TopicSeed {

    const val MOVIMIENTO = "movimiento"
    const val VELOCIDAD = "velocidad"
    const val FUERZA = "fuerza"
    const val MASA = "masa"
    const val GRAVEDAD = "gravedad"
    const val FRICCION = "friccion"
    const val ENERGIA = "energia"
    const val MAQUINAS = "maquinas_simples"

    val topics = listOf(
        PhysicsTopicEntity(
            id = MOVIMIENTO, key = MOVIMIENTO, nameEs = "Movimiento",
            descriptionEs = "Descubre cómo un objeto recorre distancia con el paso del tiempo.",
            iconEmoji = "🏃", orderIndex = 1
        ),
        PhysicsTopicEntity(
            id = VELOCIDAD, key = VELOCIDAD, nameEs = "Velocidad",
            descriptionEs = "Compara quién llega más lejos según qué tan rápido se mueve.",
            iconEmoji = "🚀", orderIndex = 2
        ),
        PhysicsTopicEntity(
            id = FUERZA, key = FUERZA, nameEs = "Fuerza",
            descriptionEs = "Empuja un carrito y observa cómo cambia su aceleración.",
            iconEmoji = "💪", orderIndex = 3
        ),
        PhysicsTopicEntity(
            id = MASA, key = MASA, nameEs = "Masa",
            descriptionEs = "Descubre por qué cuesta más mover algo pesado que algo liviano.",
            iconEmoji = "⚖️", orderIndex = 4
        ),
        PhysicsTopicEntity(
            id = GRAVEDAD, key = GRAVEDAD, nameEs = "Gravedad",
            descriptionEs = "Deja caer objetos y descubre un secreto sorprendente.",
            iconEmoji = "🍎", orderIndex = 5
        ),
        PhysicsTopicEntity(
            id = FRICCION, key = FRICCION, nameEs = "Fricción",
            descriptionEs = "Compara cómo frenan distintas superficies al mismo objeto.",
            iconEmoji = "🧊", orderIndex = 6
        ),
        PhysicsTopicEntity(
            id = ENERGIA, key = ENERGIA, nameEs = "Energía",
            descriptionEs = "Sube y baja en una montaña rusa y observa cómo cambia la energía.",
            iconEmoji = "🎢", orderIndex = 7
        ),
        PhysicsTopicEntity(
            id = MAQUINAS, key = MAQUINAS, nameEs = "Máquinas simples",
            descriptionEs = "Usa palancas, poleas y rampas para mover cargas con menos esfuerzo.",
            iconEmoji = "⚙️", orderIndex = 8
        )
    )

    val badges = listOf(
        BadgeEntity(
            id = "badge_primeros_pasos", key = "primeros_pasos",
            nameEs = "Primeros pasos", descriptionEs = "Completaste tu primer experimento.",
            iconEmoji = "🔰", criteriaEs = "Completar 1 experimento"
        ),
        BadgeEntity(
            id = "badge_explorador_movimiento", key = "explorador_movimiento",
            nameEs = "Explorador del movimiento", descriptionEs = "Dominaste los experimentos de movimiento y velocidad.",
            iconEmoji = "🏃", criteriaEs = "Completar todos los experimentos de Movimiento y Velocidad"
        ),
        BadgeEntity(
            id = "badge_fuerza_bruta", key = "fuerza_bruta",
            nameEs = "As de la fuerza", descriptionEs = "Entendiste cómo la fuerza y la masa cambian la aceleración.",
            iconEmoji = "💪", criteriaEs = "Completar todos los experimentos de Fuerza y Masa"
        ),
        BadgeEntity(
            id = "badge_cazador_gravedad", key = "cazador_gravedad",
            nameEs = "Cazador de la gravedad", descriptionEs = "Descubriste el secreto de la caída libre.",
            iconEmoji = "🍎", criteriaEs = "Completar todos los experimentos de Gravedad"
        ),
        BadgeEntity(
            id = "badge_maestro_friccion", key = "maestro_friccion",
            nameEs = "Maestro de la fricción", descriptionEs = "Comparaste el frenado en hielo, madera y alfombra.",
            iconEmoji = "🧊", criteriaEs = "Completar todos los experimentos de Fricción"
        ),
        BadgeEntity(
            id = "badge_cerebro_energetico", key = "cerebro_energetico",
            nameEs = "Cerebro energético", descriptionEs = "Comprendiste cómo se transforma la energía en la montaña rusa.",
            iconEmoji = "🎢", criteriaEs = "Completar todos los experimentos de Energía"
        ),
        BadgeEntity(
            id = "badge_ingeniero_maquinas", key = "ingeniero_maquinas",
            nameEs = "Ingeniero de máquinas", descriptionEs = "Usaste palancas, poleas y rampas como un experto.",
            iconEmoji = "⚙️", criteriaEs = "Completar todos los experimentos de Máquinas simples"
        ),
        BadgeEntity(
            id = "badge_cientifico_completo", key = "cientifico_completo",
            nameEs = "Científico completo", descriptionEs = "Completaste los 24 experimentos de Física Lab.",
            iconEmoji = "🏆", criteriaEs = "Completar los 24 experimentos"
        )
    )
}
