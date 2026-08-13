package com.kidslab.physicslab.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kidslab.physicslab.data.local.dao.BadgeDao
import com.kidslab.physicslab.data.local.dao.ExperimentDao
import com.kidslab.physicslab.data.local.dao.ExperimentParameterDao
import com.kidslab.physicslab.data.local.dao.ExperimentRunDao
import com.kidslab.physicslab.data.local.dao.ObservationDao
import com.kidslab.physicslab.data.local.dao.PhysicsTopicDao
import com.kidslab.physicslab.data.local.dao.PredictionDao
import com.kidslab.physicslab.data.local.dao.QuizAttemptDao
import com.kidslab.physicslab.data.local.dao.QuizQuestionDao
import com.kidslab.physicslab.data.local.dao.UserBadgeDao
import com.kidslab.physicslab.data.local.dao.UserProfileDao
import com.kidslab.physicslab.data.local.entity.BadgeEntity
import com.kidslab.physicslab.data.local.entity.ExperimentEntity
import com.kidslab.physicslab.data.local.entity.ExperimentParameterEntity
import com.kidslab.physicslab.data.local.entity.ExperimentRunEntity
import com.kidslab.physicslab.data.local.entity.ObservationEntity
import com.kidslab.physicslab.data.local.entity.PhysicsTopicEntity
import com.kidslab.physicslab.data.local.entity.PredictionEntity
import com.kidslab.physicslab.data.local.entity.QuizAttemptEntity
import com.kidslab.physicslab.data.local.entity.QuizQuestionEntity
import com.kidslab.physicslab.data.local.entity.UserBadgeEntity
import com.kidslab.physicslab.data.local.entity.UserProfileEntity

/**
 * Base de datos local de Física Lab. 100% offline, sin sincronización externa.
 * 11 tablas: UserProfile, PhysicsTopic, Experiment, ExperimentParameter,
 * Prediction, ExperimentRun, Observation, QuizQuestion, QuizAttempt, Badge, UserBadge.
 */
@Database(
    entities = [
        UserProfileEntity::class,
        PhysicsTopicEntity::class,
        ExperimentEntity::class,
        ExperimentParameterEntity::class,
        PredictionEntity::class,
        ExperimentRunEntity::class,
        ObservationEntity::class,
        QuizQuestionEntity::class,
        QuizAttemptEntity::class,
        BadgeEntity::class,
        UserBadgeEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun physicsTopicDao(): PhysicsTopicDao
    abstract fun experimentDao(): ExperimentDao
    abstract fun experimentParameterDao(): ExperimentParameterDao
    abstract fun predictionDao(): PredictionDao
    abstract fun experimentRunDao(): ExperimentRunDao
    abstract fun observationDao(): ObservationDao
    abstract fun quizQuestionDao(): QuizQuestionDao
    abstract fun quizAttemptDao(): QuizAttemptDao
    abstract fun badgeDao(): BadgeDao
    abstract fun userBadgeDao(): UserBadgeDao

    companion object {
        private const val DB_NAME = "fisica_lab.db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).build().also { instance = it }
            }

        /** Variante en memoria, usada en pruebas con Robolectric. */
        fun inMemory(context: Context): AppDatabase =
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }
}
