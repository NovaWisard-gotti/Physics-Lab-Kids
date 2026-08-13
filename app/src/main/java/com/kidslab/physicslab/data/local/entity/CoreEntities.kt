package com.kidslab.physicslab.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Perfil del científico junior (por ahora la app soporta un perfil local activo,
 * pero el esquema permite varios).
 */
@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val avatarId: String,
    val coatColorHex: String,
    val createdAt: Long
)

/**
 * Uno de los 8 temas de física (Movimiento, Velocidad, Fuerza, Masa,
 * Gravedad, Fricción, Energía, Máquinas simples).
 */
@Entity(tableName = "physics_topic")
data class PhysicsTopicEntity(
    @PrimaryKey val id: String,
    val key: String,
    val nameEs: String,
    val descriptionEs: String,
    val iconEmoji: String,
    val orderIndex: Int
)

@Entity(tableName = "badge")
data class BadgeEntity(
    @PrimaryKey val id: String,
    val key: String,
    val nameEs: String,
    val descriptionEs: String,
    val iconEmoji: String,
    val criteriaEs: String
)

@Entity(
    tableName = "user_badge",
    foreignKeys = [
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = BadgeEntity::class,
            parentColumns = ["id"],
            childColumns = ["badgeId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserBadgeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val badgeId: String,
    val earnedAt: Long
)
