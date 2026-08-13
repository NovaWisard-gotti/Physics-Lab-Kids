package com.kidslab.physicslab.data.seed

import com.kidslab.physicslab.data.local.AppDatabase

/**
 * Carga los datos base (8 temas, 24 experimentos con sus parámetros,
 * 30 preguntas y 8 insignias) la primera vez que se abre la app.
 * Es idempotente: si ya hay temas guardados, no vuelve a insertar nada.
 */
class SeedDataLoader(private val db: AppDatabase) {

    suspend fun seedIfNeeded() {
        if (db.physicsTopicDao().count() > 0) return

        db.physicsTopicDao().insertAll(TopicSeed.topics)
        db.badgeDao().insertAll(TopicSeed.badges)

        val allExperiments = ExperimentSeedMovement.experiments +
            ExperimentSeedForces.experiments +
            ExperimentSeedGravityFriction.experiments +
            ExperimentSeedEnergyMachines.experiments

        val allParameters = ExperimentSeedMovement.parameters +
            ExperimentSeedForces.parameters +
            ExperimentSeedGravityFriction.parameters +
            ExperimentSeedEnergyMachines.parameters

        db.experimentDao().insertAll(allExperiments)
        db.experimentParameterDao().insertAll(allParameters)

        db.quizQuestionDao().insertAll(QuizSeed.questions)
    }
}
