package com.kidslab.physicslab.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kidslab.physicslab.data.local.entity.BadgeEntity
import com.kidslab.physicslab.data.local.entity.UserBadgeEntity
import com.kidslab.physicslab.data.local.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: UserProfileEntity): Long

    @Update
    suspend fun update(profile: UserProfileEntity)

    @Query("SELECT * FROM user_profile ORDER BY id DESC LIMIT 1")
    fun observeActiveProfile(): Flow<UserProfileEntity?>

    @Query("SELECT * FROM user_profile ORDER BY id DESC LIMIT 1")
    suspend fun getActiveProfile(): UserProfileEntity?
}

@Dao
interface BadgeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(badges: List<BadgeEntity>)

    @Query("SELECT * FROM badge")
    fun observeAllBadges(): Flow<List<BadgeEntity>>

    @Query("SELECT COUNT(*) FROM badge")
    suspend fun count(): Int
}

@Dao
interface UserBadgeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userBadge: UserBadgeEntity)

    @Query("SELECT * FROM user_badge WHERE userId = :userId")
    fun observeForUser(userId: Long): Flow<List<UserBadgeEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM user_badge WHERE userId = :userId AND badgeId = :badgeId)")
    suspend fun hasBadge(userId: Long, badgeId: String): Boolean
}
