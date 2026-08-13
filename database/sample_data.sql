-- Física Lab — datos de ejemplo (subconjunto representativo, no exhaustivo).
-- Los 8 temas, 24 experimentos y 30 preguntas completos viven en el código
-- Kotlin de app/src/main/java/com/kidslab/physicslab/data/seed/.
-- Este archivo es una referencia rápida en SQL puro.

-- 8 temas
INSERT INTO physics_topic (id, key, nameEs, descriptionEs, iconEmoji, orderIndex) VALUES
('movimiento', 'movimiento', 'Movimiento', 'Descubre cómo un objeto recorre distancia con el paso del tiempo.', '🏃', 1),
('velocidad', 'velocidad', 'Velocidad', 'Compara quién llega más lejos según qué tan rápido se mueve.', '🚀', 2),
('fuerza', 'fuerza', 'Fuerza', 'Empuja un carrito y observa cómo cambia su aceleración.', '💪', 3),
('masa', 'masa', 'Masa', 'Descubre por qué cuesta más mover algo pesado que algo liviano.', '⚖️', 4),
('gravedad', 'gravedad', 'Gravedad', 'Deja caer objetos y descubre un secreto sorprendente.', '🍎', 5),
('friccion', 'friccion', 'Fricción', 'Compara cómo frenan distintas superficies al mismo objeto.', '🧊', 6),
('energia', 'energia', 'Energía', 'Sube y baja en una montaña rusa y observa cómo cambia la energía.', '🎢', 7),
('maquinas_simples', 'maquinas_simples', 'Máquinas simples', 'Usa palancas, poleas y rampas para mover cargas con menos esfuerzo.', '⚙️', 8);

-- Experimentos de ejemplo (3 de los 24, uno por dificultad)
INSERT INTO experiment (id, topicId, titleEs, instructionEs, predictQuestionEs, explanationEs, difficulty, orderIndex) VALUES
('mov_01', 'movimiento', 'Pista corta',
 'Elige una velocidad inicial y un tiempo, y observa cuánto avanza el carrito por la pista.',
 'Si el carrito va más rápido, ¿crees que recorrerá más distancia o menos distancia en el mismo tiempo?',
 'La distancia recorrida es el resultado de multiplicar la velocidad por el tiempo. A más velocidad, con el mismo tiempo, el carrito llega más lejos.',
 1, 1),
('gra_01', 'gravedad', 'Misma altura, distinta masa',
 'Deja caer una pelota liviana y una pelota pesada desde la misma altura, en la Tierra.',
 '¿Cuál pelota crees que llegará primero al suelo: la liviana o la pesada?',
 'En una caída ideal, sin aire, ambas pelotas llegan al mismo tiempo. La masa no cambia el tiempo de caída, ¡solo la gravedad del lugar!',
 2, 1),
('ene_03', 'energia', 'Montaña alta',
 'Prueba la colina más alta de todas y observa el punto donde las barras se cruzan.',
 '¿En qué momento crees que la energía de altura y la de movimiento son exactamente iguales?',
 'A la mitad de la bajada, la energía potencial y la cinética suelen ser aproximadamente iguales: la energía total no cambia, solo se transforma.',
 3, 3);

-- Parámetros de ejemplo para "mov_01"
INSERT INTO experiment_parameter (experimentId, paramKey, labelEs, unitSymbol, minValue, maxValue, defaultValue) VALUES
('mov_01', 'initialVelocity', 'Velocidad inicial', 'm/s', 0.5, 5.0, 2.0),
('mov_01', 'time', 'Tiempo', 's', 1.0, 5.0, 3.0);

-- 8 insignias
INSERT INTO badge (id, key, nameEs, descriptionEs, iconEmoji, criteriaEs) VALUES
('badge_primeros_pasos', 'primeros_pasos', 'Primeros pasos', 'Completaste tu primer experimento.', '🔰', 'Completar 1 experimento'),
('badge_explorador_movimiento', 'explorador_movimiento', 'Explorador del movimiento', 'Dominaste los experimentos de movimiento y velocidad.', '🏃', 'Completar todos los experimentos de Movimiento y Velocidad'),
('badge_fuerza_bruta', 'fuerza_bruta', 'As de la fuerza', 'Entendiste cómo la fuerza y la masa cambian la aceleración.', '💪', 'Completar todos los experimentos de Fuerza y Masa'),
('badge_cazador_gravedad', 'cazador_gravedad', 'Cazador de la gravedad', 'Descubriste el secreto de la caída libre.', '🍎', 'Completar todos los experimentos de Gravedad'),
('badge_maestro_friccion', 'maestro_friccion', 'Maestro de la fricción', 'Comparaste el frenado en hielo, madera y alfombra.', '🧊', 'Completar todos los experimentos de Fricción'),
('badge_cerebro_energetico', 'cerebro_energetico', 'Cerebro energético', 'Comprendiste cómo se transforma la energía en la montaña rusa.', '🎢', 'Completar todos los experimentos de Energía'),
('badge_ingeniero_maquinas', 'ingeniero_maquinas', 'Ingeniero de máquinas', 'Usaste palancas, poleas y rampas como un experto.', '⚙️', 'Completar todos los experimentos de Máquinas simples'),
('badge_cientifico_completo', 'cientifico_completo', 'Científico completo', 'Completaste los 24 experimentos de Física Lab.', '🏆', 'Completar los 24 experimentos');

-- Preguntas de ejemplo (3 de las 30)
INSERT INTO quiz_question (id, topicId, questionEs, optionAEs, optionBEs, optionCEs, correctOption) VALUES
('q_mov_01', 'movimiento', '¿Qué necesitas para calcular la distancia que recorre un objeto?', 'Solo su color', 'Su velocidad y el tiempo', 'Su tamaño', 'B'),
('q_gra_01', 'gravedad', 'En una caída ideal sin aire, ¿qué objeto llega primero al soltarlos juntos desde la misma altura?', 'El más pesado', 'El más liviano', 'Ambos llegan juntos', 'C'),
('q_maq_01', 'maquinas_simples', 'Una palanca sirve para...', 'Multiplicar tu fuerza', 'Eliminar la gravedad', 'Hacer más pesada la carga', 'A');

-- Ejemplo de un científico junior con progreso
INSERT INTO user_profile (id, name, avatarId, coatColorHex, createdAt) VALUES
(1, 'Ada', '🧑‍🔬', '#2F6FED', 1750000000000);

INSERT INTO prediction (experimentId, userId, predictedOptionEs, createdAt) VALUES
('mov_01', 1, 'Recorrerá más distancia', 1750000010000);

INSERT INTO experiment_run (experimentId, userId, inputParamsJson, resultValue, resultUnitSymbol, ranAt) VALUES
('mov_01', 1, '{"initialVelocity":3.0,"time":3.0}', 9.0, 'm', 1750000020000);

INSERT INTO observation (experimentRunId, observationEs, matchedPrediction) VALUES
(1, 'El carrito recorrió 9.0 m en 3.0 s.', 1);

INSERT INTO user_badge (userId, badgeId, earnedAt) VALUES
(1, 'badge_primeros_pasos', 1750000020000);
