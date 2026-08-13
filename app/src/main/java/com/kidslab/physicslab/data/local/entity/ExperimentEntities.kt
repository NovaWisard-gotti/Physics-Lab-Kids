package com.kidslab.physicslab.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Un experimento guiado dentro de un laboratorio/tema.
 * La app incluye un mínimo de 24 experimentos guiados repartidos en 8 temas.
 */
@Entity(
    tableName = "experiment",
    foreignKeys = [
        ForeignKey(
            entity = PhysicsTopicEntity::class,
            parentColumns = ["id"],
            childColumns = ["topicId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("topicId")]
)
data class ExperimentEntity(
    @PrimaryKey val id: String,
    val topicId: String,
    val titleEs: String,
    val instructionEs: String,
    val predictQuestionEs: String,
    val explanationEs: String,
    val difficulty: Int, // 1..3
    val orderIndex: Int
)

/** Un control/parametro ajustable dentro de un experimento (slider, chips, etc). */
@Entity(
    tableName = "experiment_parameter",
    foreignKeys = [
        ForeignKey(
            entity = ExperimentEntity::class,
            parentColumns = ["id"],
            childColumns = ["experimentId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("experimentId")]
)
data class ExperimentParameterEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val experimentId: String,
    val paramKey: String,
    val labelEs: String,
    val unitSymbol: String,
    val minValue: Double,
    val maxValue: Double,
    val defaultValue: Double
)

/** La predicción del niño antes de correr el experimento. */
@Entity(
    tableName = "prediction",
    foreignKeys = [
        ForeignKey(
            entity = ExperimentEntity::class,
            parentColumns = ["id"],
            childColumns = ["experimentId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("experimentId"), Index("userId")]
)
data class PredictionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val experimentId: String,
    val userId: Long,
    val predictedOptionEs: String,
    val createdAt: Long
)

/** Una ejecución del experimento con los parámetros elegidos y el resultado. */
@Entity(
    tableName = "experiment_run",
    foreignKeys = [
        ForeignKey(
            entity = ExperimentEntity::class,
            parentColumns = ["id"],
            childColumns = ["experimentId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("experimentId"), Index("userId")]
)
data class ExperimentRunEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val experimentId: String,
    val userId: Long,
    val inputParamsJson: String,
    val resultValue: Double,
    val resultUnitSymbol: String,
    val ranAt: Long
)

/** Comparación entre la predicción y el resultado real (para la etapa "Explica"). */
@Entity(
    tableName = "observation",
    foreignKeys = [
        ForeignKey(
            entity = ExperimentRunEntity::class,
            parentColumns = ["id"],
            childColumns = ["experimentRunId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("experimentRunId")]
)
data class ObservationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val experimentRunId: Long,
    val observationEs: String,
    val matchedPrediction: Boolean
)
