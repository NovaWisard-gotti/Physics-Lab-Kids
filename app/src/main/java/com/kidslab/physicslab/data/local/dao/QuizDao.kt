package com.kidslab.physicslab.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kidslab.physicslab.data.local.entity.QuizAttemptEntity
import com.kidslab.physicslab.data.local.entity.QuizQuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizQuestionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(questions: List<QuizQuestionEntity>)

    @Query("SELECT * FROM quiz_question ORDER BY RANDOM()")
    suspend fun getAllShuffled(): List<QuizQuestionEntity>

    @Query("SELECT * FROM quiz_question WHERE topicId = :topicId")
    fun observeByTopic(topicId: String): Flow<List<QuizQuestionEntity>>

    @Query("SELECT COUNT(*) FROM quiz_question")
    suspend fun count(): Int
}

@Dao
interface QuizAttemptDao {
    @Insert
    suspend fun insert(attempt: QuizAttemptEntity): Long

    @Query("SELECT * FROM quiz_attempt WHERE userId = :userId")
    fun observeForUser(userId: Long): Flow<List<QuizAttemptEntity>>

    @Query("SELECT COUNT(*) FROM quiz_attempt WHERE userId = :userId AND correct = 1")
    suspend fun correctCount(userId: Long): Int
}
