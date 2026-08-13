package com.kidslab.physicslab.data.repository

import com.kidslab.physicslab.data.local.AppDatabase
import com.kidslab.physicslab.data.local.entity.ExperimentRunEntity
import com.kidslab.physicslab.data.local.entity.ObservationEntity
import com.kidslab.physicslab.data.local.entity.PredictionEntity
import com.kidslab.physicslab.data.local.entity.QuizAttemptEntity
import com.kidslab.physicslab.data.local.entity.UserBadgeEntity
import com.kidslab.physicslab.data.local.entity.UserProfileEntity
import com.kidslab.physicslab.data.seed.TopicSeed
import kotlinx.coroutines.flow.Flow

class PhysicsLabRepository(private val db: AppDatabase) {

    // --- Perfil ---
    fun observeActiveProfile(): Flow<UserProfileEntity?> = db.userProfileDao().observeActiveProfile()

    suspend fun getActiveProfile(): UserProfileEntity? = db.userProfileDao().getActiveProfile()

    suspend fun createOrUpdateProfile(name: String, avatarId: String, coatColorHex: String): Long {
        val existing = db.userProfileDao().getActiveProfile()
        return if (existing != null) {
            db.userProfileDao().update(existing.copy(name = name, avatarId = avatarId, coatColorHex = coatColorHex))
            existing.id
        } else {
            db.userProfileDao().insert(
                UserProfileEntity(
                    name = name,
                    avatarId = avatarId,
                    coatColorHex = coatColorHex,
                    createdAt = System.currentTimeMillis()
                )
            )
        }
    }

    // --- Temas y experimentos ---
    fun observeTopics() = db.physicsTopicDao().observeAllTopics()
    fun observeExperimentsByTopic(topicId: String) = db.experimentDao().observeByTopic(topicId)
    fun observeParametersByExperiment(experimentId: String) = db.experimentParameterDao().observeByExperiment(experimentId)
    suspend fun getExperiment(experimentId: String) = db.experimentDao().getExperiment(experimentId)
    suspend fun getTopic(topicId: String) = db.physicsTopicDao().getTopic(topicId)

    // --- Predicción, ejecución y observación (flujo PEOE) ---
    suspend fun savePrediction(experimentId: String, userId: Long, predictedOptionEs: String): Long =
        db.predictionDao().insert(
            PredictionEntity(
                experimentId = experimentId,
                userId = userId,
                predictedOptionEs = predictedOptionEs,
                createdAt = System.currentTimeMillis()
            )
        )

    suspend fun saveExperimentRun(
        experimentId: String,
        userId: Long,
        inputParamsJson: String,
        resultValue: Double,
        resultUnitSymbol: String
    ): Long {
        val runId = db.experimentRunDao().insert(
            ExperimentRunEntity(
                experimentId = experimentId,
                userId = userId,
                inputParamsJson = inputParamsJson,
                resultValue = resultValue,
                resultUnitSymbol = resultUnitSymbol,
                ranAt = System.currentTimeMillis()
            )
        )
        awardBadgesIfNeeded(userId)
        return runId
    }

    suspend fun saveObservation(runId: Long, observationEs: String, matchedPrediction: Boolean): Long =
        db.observationDao().insert(
            ObservationEntity(
                experimentRunId = runId,
                observationEs = observationEs,
                matchedPrediction = matchedPrediction
            )
        )

    fun observeCompletedExperimentCount(userId: Long): Flow<Int> =
        db.experimentRunDao().observeCompletedExperimentCount(userId)

    // --- Quiz ---
    suspend fun getShuffledQuizQuestions() = db.quizQuestionDao().getAllShuffled()

    suspend fun saveQuizAttempt(quizQuestionId: String, userId: Long, selectedOption: String, correct: Boolean): Long =
        db.quizAttemptDao().insert(
            QuizAttemptEntity(
                quizQuestionId = quizQuestionId,
                userId = userId,
                selectedOption = selectedOption,
                correct = correct,
                attemptedAt = System.currentTimeMillis()
            )
        ).also { awardBadgesIfNeeded(userId) }

    // --- Insignias ---
    fun observeUserBadges(userId: Long) = db.userBadgeDao().observeForUser(userId)
    fun observeAllBadges() = db.badgeDao().observeAllBadges()

    /** Revisa el progreso del usuario y otorga insignias nuevas si corresponde. */
    private suspend fun awardBadgesIfNeeded(userId: Long) {
        val completedIds = db.experimentRunDao().completedExperimentIds(userId).toSet()

        maybeAward(userId, "badge_primeros_pasos", completedIds.isNotEmpty())

        val movementVelocityIds = setOf("mov_01", "mov_02", "mov_03", "vel_01", "vel_02", "vel_03")
        maybeAward(userId, "badge_explorador_movimiento", completedIds.containsAll(movementVelocityIds))

        val forceMassIds = setOf("fue_01", "fue_02", "fue_03", "masa_01", "masa_02", "masa_03")
        maybeAward(userId, "badge_fuerza_bruta", completedIds.containsAll(forceMassIds))

        val gravityIds = setOf("gra_01", "gra_02", "gra_03")
        maybeAward(userId, "badge_cazador_gravedad", completedIds.containsAll(gravityIds))

        val frictionIds = setOf("fri_01", "fri_02", "fri_03")
        maybeAward(userId, "badge_maestro_friccion", completedIds.containsAll(frictionIds))

        val energyIds = setOf("ene_01", "ene_02", "ene_03")
        maybeAward(userId, "badge_cerebro_energetico", completedIds.containsAll(energyIds))

        val machineIds = setOf("maq_01", "maq_02", "maq_03")
        maybeAward(userId, "badge_ingeniero_maquinas", completedIds.containsAll(machineIds))

        val allExperimentIds = movementVelocityIds + forceMassIds + gravityIds + frictionIds + energyIds + machineIds
        maybeAward(userId, "badge_cientifico_completo", completedIds.containsAll(allExperimentIds))
    }

    private suspend fun maybeAward(userId: Long, badgeId: String, condition: Boolean) {
        if (!condition) return
        if (db.userBadgeDao().hasBadge(userId, badgeId)) return
        db.userBadgeDao().insert(
            UserBadgeEntity(userId = userId, badgeId = badgeId, earnedAt = System.currentTimeMillis())
        )
    }

    companion object {
        val ALL_TOPIC_IDS = listOf(
            TopicSeed.MOVIMIENTO, TopicSeed.VELOCIDAD, TopicSeed.FUERZA, TopicSeed.MASA,
            TopicSeed.GRAVEDAD, TopicSeed.FRICCION, TopicSeed.ENERGIA, TopicSeed.MAQUINAS
        )
    }
}
