package com.kidslab.physicslab.di

import android.content.Context
import com.kidslab.physicslab.data.local.AppDatabase
import com.kidslab.physicslab.data.repository.PhysicsLabRepository
import com.kidslab.physicslab.data.seed.SeedDataLoader

/**
 * Contenedor manual de dependencias (sin Hilt/Dagger), siguiendo el mismo
 * patrón usado en HabitHero y Money Explorer.
 */
class AppContainer(context: Context) {
    val database: AppDatabase = AppDatabase.getInstance(context)
    val repository: PhysicsLabRepository = PhysicsLabRepository(database)
    val seedDataLoader: SeedDataLoader = SeedDataLoader(database)
}
