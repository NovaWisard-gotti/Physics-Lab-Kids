package com.kidslab.physicslab.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kidslab.physicslab.data.local.entity.ExperimentRunEntity
import com.kidslab.physicslab.data.local.entity.ObservationEntity
import com.kidslab.physicslab.data.local.entity.PredictionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PredictionDao {
    @Insert
    suspend fun insert(prediction: PredictionEntity): Long

    @Query("SELECT * FROM prediction WHERE experimentId = :experimentId AND userId = :userId ORDER BY createdAt DESC LIMIT 1")
    suspend fun getLatest(experimentId: String, userId: Long): PredictionEntity?
}

@Dao
interface ExperimentRunDao {
    @Insert
    suspend fun insert(run: ExperimentRunEntity): Long

    @Query("SELECT * FROM experiment_run WHERE userId = :userId")
    fun observeForUser(userId: Long): Flow<List<ExperimentRunEntity>>

    @Query("SELECT COUNT(DISTINCT experimentId) FROM experiment_run WHERE userId = :userId")
    fun observeCompletedExperimentCount(userId: Long): Flow<Int>

    @Query("SELECT COUNT(DISTINCT experimentId) FROM experiment_run WHERE userId = :userId")
    suspend fun completedExperimentCount(userId: Long): Int

    @Query("SELECT DISTINCT experimentId FROM experiment_run WHERE userId = :userId")
    suspend fun completedExperimentIds(userId: Long): List<String>
}

@Dao
interface ObservationDao {
    @Insert
    suspend fun insert(observation: ObservationEntity): Long

    @Query("SELECT * FROM observation WHERE experimentRunId = :runId")
    suspend fun getForRun(runId: Long): ObservationEntity?
}
