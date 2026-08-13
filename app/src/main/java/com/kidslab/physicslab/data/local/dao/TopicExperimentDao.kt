package com.kidslab.physicslab.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kidslab.physicslab.data.local.entity.ExperimentEntity
import com.kidslab.physicslab.data.local.entity.ExperimentParameterEntity
import com.kidslab.physicslab.data.local.entity.PhysicsTopicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhysicsTopicDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(topics: List<PhysicsTopicEntity>)

    @Query("SELECT * FROM physics_topic ORDER BY orderIndex ASC")
    fun observeAllTopics(): Flow<List<PhysicsTopicEntity>>

    @Query("SELECT * FROM physics_topic WHERE id = :topicId")
    suspend fun getTopic(topicId: String): PhysicsTopicEntity?

    @Query("SELECT COUNT(*) FROM physics_topic")
    suspend fun count(): Int
}

@Dao
interface ExperimentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(experiments: List<ExperimentEntity>)

    @Query("SELECT * FROM experiment WHERE topicId = :topicId ORDER BY orderIndex ASC")
    fun observeByTopic(topicId: String): Flow<List<ExperimentEntity>>

    @Query("SELECT * FROM experiment WHERE id = :experimentId")
    suspend fun getExperiment(experimentId: String): ExperimentEntity?

    @Query("SELECT COUNT(*) FROM experiment")
    suspend fun count(): Int
}

@Dao
interface ExperimentParameterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(parameters: List<ExperimentParameterEntity>)

    @Query("SELECT * FROM experiment_parameter WHERE experimentId = :experimentId")
    fun observeByExperiment(experimentId: String): Flow<List<ExperimentParameterEntity>>
}
