package com.kidslab.physicslab.data

import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.kidslab.physicslab.data.local.AppDatabase
import com.kidslab.physicslab.data.repository.PhysicsLabRepository
import com.kidslab.physicslab.data.seed.SeedDataLoader
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class PhysicsLabRepositoryTest {

    private lateinit var db: AppDatabase
    private lateinit var repository: PhysicsLabRepository

    @Before
    fun setUp() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        db = AppDatabase.inMemory(context)
        repository = PhysicsLabRepository(db)
        SeedDataLoader(db).seedIfNeeded()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `la semilla carga 8 temas 24 experimentos 30 preguntas y 8 insignias`() = runBlocking {
        assertThat(db.physicsTopicDao().count()).isEqualTo(8)
        assertThat(db.experimentDao().count()).isEqualTo(24)
        assertThat(db.quizQuestionDao().count()).isEqualTo(30)
        assertThat(db.badgeDao().count()).isEqualTo(8)
    }

    @Test
    fun `crear perfil persiste el nombre del cientifico junior`() = runBlocking {
        val userId = repository.createOrUpdateProfile("Ada", "🧑\u200D🔬", "#2F6FED")
        val profile = repository.getActiveProfile()
        assertThat(profile).isNotNull()
        assertThat(profile!!.name).isEqualTo("Ada")
        assertThat(profile.id).isEqualTo(userId)
    }

    @Test
    fun `guardar una prediccion la deja disponible para el mismo experimento y usuario`() = runBlocking {
        val userId = repository.createOrUpdateProfile("Ada", "🧑\u200D🔬", "#2F6FED")
        repository.savePrediction("mov_01", userId, "Recorrerá más distancia")
        val latest = db.predictionDao().getLatest("mov_01", userId)
        assertThat(latest).isNotNull()
        assertThat(latest!!.predictedOptionEs).isEqualTo("Recorrerá más distancia")
    }

    @Test
    fun `guardar una ejecucion incrementa el progreso de experimentos completados`() = runBlocking {
        val userId = repository.createOrUpdateProfile("Ada", "🧑\u200D🔬", "#2F6FED")
        assertThat(db.experimentRunDao().completedExperimentCount(userId)).isEqualTo(0)

        repository.saveExperimentRun("mov_01", userId, "{}", 6.0, "m")
        assertThat(db.experimentRunDao().completedExperimentCount(userId)).isEqualTo(1)

        repository.saveExperimentRun("mov_01", userId, "{}", 8.0, "m")
        assertThat(db.experimentRunDao().completedExperimentCount(userId))
            .isEqualTo(1) // el mismo experimento repetido no duplica el progreso

        repository.saveExperimentRun("mov_02", userId, "{}", 10.0, "m")
        assertThat(db.experimentRunDao().completedExperimentCount(userId)).isEqualTo(2)
    }

    @Test
    fun `completar el primer experimento otorga la insignia primeros pasos`() = runBlocking {
        val userId = repository.createOrUpdateProfile("Ada", "🧑\u200D🔬", "#2F6FED")
        repository.saveExperimentRun("mov_01", userId, "{}", 6.0, "m")
        val earned = repository.observeUserBadges(userId).first()
        assertThat(earned.map { it.badgeId }).contains("badge_primeros_pasos")
    }

    @Test
    fun `completar los 24 experimentos otorga la insignia cientifico completo`() = runBlocking {
        val userId = repository.createOrUpdateProfile("Ada", "🧑\u200D🔬", "#2F6FED")
        val allExperimentIds = listOf(
            "mov_01", "mov_02", "mov_03", "vel_01", "vel_02", "vel_03",
            "fue_01", "fue_02", "fue_03", "masa_01", "masa_02", "masa_03",
            "gra_01", "gra_02", "gra_03", "fri_01", "fri_02", "fri_03",
            "ene_01", "ene_02", "ene_03", "maq_01", "maq_02", "maq_03"
        )
        allExperimentIds.forEach { repository.saveExperimentRun(it, userId, "{}", 1.0, "m") }
        val earned = repository.observeUserBadges(userId).first()
        assertThat(earned.map { it.badgeId }).contains("badge_cientifico_completo")
    }

    @Test
    fun `los datos persisten al reabrir la misma instancia de base de datos`() = runBlocking {
        val userId = repository.createOrUpdateProfile("Luca", "🧑\u200D🔬", "#33C481")
        repository.saveExperimentRun("fri_01", userId, "{}", 3.5, "m")

        // Releemos directamente desde la base, simulando una nueva sesión en memoria.
        val storedProfile = db.userProfileDao().getActiveProfile()
        val storedRuns = db.experimentRunDao().completedExperimentCount(userId)

        assertThat(storedProfile?.name).isEqualTo("Luca")
        assertThat(storedRuns).isEqualTo(1)
    }
}
