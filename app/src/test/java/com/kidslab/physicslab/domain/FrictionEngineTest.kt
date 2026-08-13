package com.kidslab.physicslab.domain

import com.google.common.truth.Truth.assertThat
import com.kidslab.physicslab.domain.engine.FrictionEngine
import com.kidslab.physicslab.domain.model.SurfaceType
import org.junit.Test

class FrictionEngineTest {

    @Test
    fun `el hielo permite recorrer mas distancia que la madera`() {
        val ice = FrictionEngine.simulate(SurfaceType.ICE, 5.0)
        val wood = FrictionEngine.simulate(SurfaceType.WOOD, 5.0)
        assertThat(ice.stoppingDistanceM).isGreaterThan(wood.stoppingDistanceM)
    }

    @Test
    fun `la madera permite recorrer mas distancia que la alfombra`() {
        val wood = FrictionEngine.simulate(SurfaceType.WOOD, 5.0)
        val carpet = FrictionEngine.simulate(SurfaceType.CARPET, 5.0)
        assertThat(wood.stoppingDistanceM).isGreaterThan(carpet.stoppingDistanceM)
    }

    @Test
    fun `mismo impulso mayor velocidad recorre mas distancia`() {
        val slow = FrictionEngine.simulate(SurfaceType.WOOD, 2.0)
        val fast = FrictionEngine.simulate(SurfaceType.WOOD, 6.0)
        assertThat(fast.stoppingDistanceM).isGreaterThan(slow.stoppingDistanceM)
    }

    @Test
    fun `simulateAll devuelve las tres superficies ordenadas por friccion`() {
        val results = FrictionEngine.simulateAll(4.0)
        assertThat(results).hasSize(3)
        val distances = results.associate { it.surface to it.stoppingDistanceM }
        assertThat(distances[SurfaceType.ICE]!!).isGreaterThan(distances[SurfaceType.WOOD]!!)
        assertThat(distances[SurfaceType.WOOD]!!).isGreaterThan(distances[SurfaceType.CARPET]!!)
    }
}
