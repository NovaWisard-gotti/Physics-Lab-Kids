package com.kidslab.physicslab.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/** Pregunta de opción múltiple (30 en total, repartidas entre los 8 temas). */
@Entity(
    tableName = "quiz_question",
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
data class QuizQuestionEntity(
    @PrimaryKey val id: String,
    val topicId: String,
    val questionEs: String,
    val optionAEs: String,
    val optionBEs: String,
    val optionCEs: String,
    val correctOption: String // "A", "B" o "C"
)

@Entity(
    tableName = "quiz_attempt",
    foreignKeys = [
        ForeignKey(
            entity = QuizQuestionEntity::class,
            parentColumns = ["id"],
            childColumns = ["quizQuestionId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("quizQuestionId"), Index("userId")]
)
data class QuizAttemptEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val quizQuestionId: String,
    val userId: Long,
    val selectedOption: String,
    val correct: Boolean,
    val attemptedAt: Long
)
