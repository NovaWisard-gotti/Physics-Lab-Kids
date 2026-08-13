package com.kidslab.physicslab.data.seed

import com.kidslab.physicslab.data.local.entity.QuizQuestionEntity

/** 30 preguntas de opción múltiple, repartidas entre los 8 temas. */
object QuizSeed {

    val questions = listOf(
        // Movimiento (4)
        QuizQuestionEntity("q_mov_01", TopicSeed.MOVIMIENTO, "¿Qué necesitas para calcular la distancia que recorre un objeto?", "Solo su color", "Su velocidad y el tiempo", "Su tamaño", "B"),
        QuizQuestionEntity("q_mov_02", TopicSeed.MOVIMIENTO, "Si un carrito va a 2 m/s durante 3 segundos, ¿cuánto recorre?", "5 m", "6 m", "1 m", "B"),
        QuizQuestionEntity("q_mov_03", TopicSeed.MOVIMIENTO, "¿En qué unidad medimos la distancia en Física Lab?", "Metros (m)", "Kilos (kg)", "Newtons (N)", "A"),
        QuizQuestionEntity("q_mov_04", TopicSeed.MOVIMIENTO, "Si duplicas el tiempo y la velocidad no cambia, la distancia...", "Se reduce a la mitad", "No cambia", "Se duplica", "C"),

        // Velocidad (4)
        QuizQuestionEntity("q_vel_01", TopicSeed.VELOCIDAD, "En una carrera con el mismo tiempo, ¿quién gana?", "El que tiene más masa", "El más rápido", "El más pesado", "B"),
        QuizQuestionEntity("q_vel_02", TopicSeed.VELOCIDAD, "¿En qué unidad medimos la velocidad?", "m/s", "kg", "N", "A"),
        QuizQuestionEntity("q_vel_03", TopicSeed.VELOCIDAD, "Si dos carritos parten juntos y uno va más rápido, con el tiempo...", "Se mantienen juntos", "El rápido se adelanta", "El lento se adelanta", "B"),
        QuizQuestionEntity("q_vel_04", TopicSeed.VELOCIDAD, "Si aumentas la velocidad al doble con el mismo tiempo, la distancia...", "Se duplica", "Se reduce a la mitad", "No cambia", "A"),

        // Fuerza (4)
        QuizQuestionEntity("q_fue_01", TopicSeed.FUERZA, "Con la misma masa, si aplicas más fuerza, la aceleración...", "Disminuye", "Aumenta", "No cambia", "B"),
        QuizQuestionEntity("q_fue_02", TopicSeed.FUERZA, "¿En qué unidad medimos la fuerza?", "Metros", "Newtons (N)", "Segundos", "B"),
        QuizQuestionEntity("q_fue_03", TopicSeed.FUERZA, "¿Qué produce que un carrito acelere?", "El color del carrito", "Una fuerza aplicada sobre él", "El tamaño de sus ruedas", "B"),
        QuizQuestionEntity("q_fue_04", TopicSeed.FUERZA, "Si no aplicas ninguna fuerza a un carrito quieto, este...", "Se mueve solo", "Se queda quieto", "Vuela", "B"),

        // Masa (4)
        QuizQuestionEntity("q_masa_01", TopicSeed.MASA, "Con la misma fuerza, si la masa es mayor, la aceleración...", "Aumenta", "Disminuye", "No cambia", "B"),
        QuizQuestionEntity("q_masa_02", TopicSeed.MASA, "¿En qué unidad medimos la masa?", "Kilogramos (kg)", "Newtons (N)", "Metros (m)", "A"),
        QuizQuestionEntity("q_masa_03", TopicSeed.MASA, "¿Cuál cuesta más empujar con la misma fuerza?", "Un carrito liviano", "Un carrito pesado", "Ambos igual", "B"),
        QuizQuestionEntity("q_masa_04", TopicSeed.MASA, "La masa mide...", "Qué tan rápido va algo", "Cuánta materia tiene un objeto", "Qué tan lejos llega algo", "B"),

        // Gravedad (4)
        QuizQuestionEntity("q_gra_01", TopicSeed.GRAVEDAD, "En una caída ideal sin aire, ¿qué objeto llega primero al soltarlos juntos desde la misma altura?", "El más pesado", "El más liviano", "Ambos llegan juntos", "C"),
        QuizQuestionEntity("q_gra_02", TopicSeed.GRAVEDAD, "¿Dónde cae más lento un objeto: en la Tierra o en la Luna?", "En la Tierra", "En la Luna", "Igual en ambas", "B"),
        QuizQuestionEntity("q_gra_03", TopicSeed.GRAVEDAD, "Si sueltas un objeto desde más alto, el tiempo de caída...", "Aumenta", "Disminuye", "No cambia", "A"),
        QuizQuestionEntity("q_gra_04", TopicSeed.GRAVEDAD, "La gravedad es la fuerza que...", "Empuja los objetos hacia arriba", "Atrae los objetos hacia abajo", "Detiene el movimiento", "B"),

        // Fricción (4)
        QuizQuestionEntity("q_fri_01", TopicSeed.FRICCION, "¿Qué superficie frena menos a un objeto que se desliza?", "Alfombra", "Hielo", "Madera", "B"),
        QuizQuestionEntity("q_fri_02", TopicSeed.FRICCION, "¿Qué superficie tiene más fricción?", "Hielo", "Madera", "Alfombra", "C"),
        QuizQuestionEntity("q_fri_03", TopicSeed.FRICCION, "La fricción es una fuerza que...", "Empuja los objetos hacia adelante", "Se opone al movimiento", "Aumenta la velocidad", "B"),
        QuizQuestionEntity("q_fri_04", TopicSeed.FRICCION, "Con el mismo impulso, ¿dónde llega más lejos un objeto?", "En alfombra", "En hielo", "En madera", "B"),

        // Energía (3)
        QuizQuestionEntity("q_ene_01", TopicSeed.ENERGIA, "Cuando un carrito está en lo más alto de la colina, tiene más...", "Energía cinética", "Energía potencial", "Ninguna energía", "B"),
        QuizQuestionEntity("q_ene_02", TopicSeed.ENERGIA, "Cuando el carrito va bajando, la energía potencial se transforma en...", "Energía cinética", "Energía eléctrica", "Nada, desaparece", "A"),
        QuizQuestionEntity("q_ene_03", TopicSeed.ENERGIA, "¿Qué determina cuánta energía potencial tiene un objeto arriba de una colina?", "Su color", "Su altura", "Su forma", "B"),

        // Máquinas simples (3)
        QuizQuestionEntity("q_maq_01", TopicSeed.MAQUINAS, "Una palanca sirve para...", "Multiplicar tu fuerza", "Eliminar la gravedad", "Hacer más pesada la carga", "A"),
        QuizQuestionEntity("q_maq_02", TopicSeed.MAQUINAS, "Con más cuerdas de apoyo en una polea, la fuerza necesaria...", "Aumenta", "Disminuye", "No cambia", "B"),
        QuizQuestionEntity("q_maq_03", TopicSeed.MAQUINAS, "Una rampa (plano inclinado) más larga para la misma altura necesita...", "Más fuerza", "Menos fuerza", "La misma fuerza", "B")
    )
}
