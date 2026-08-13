package com.kidslab.physicslab.domain

import com.google.common.truth.Truth.assertThat
import com.kidslab.physicslab.domain.engine.ForceMassEngine
import com.kidslab.physicslab.domain.model.IntensityLevel
import org.junit.Test

class ForceMassEngineTest {

    @Test
    fun `mas fuerza con misma masa produce mas aceleracion`() {
        val low = ForceMassEngine.simulate(IntensityLevel.LOW, IntensityLevel.MEDIUM)
        val high = ForceMassEngine.simulate(IntensityLevel.HIGH, IntensityLevel.MEDIUM)
        assertThat(high.accelerationMs2).isGreaterThan(low.accelerationMs2)
    }

    @Test
    fun `mas masa con misma fuerza produce menos aceleracion`() {
        val lowMass = ForceMassEngine.simulate(IntensityLevel.MEDIUM, IntensityLevel.LOW)
        val highMass = ForceMassEngine.simulate(IntensityLevel.MEDIUM, IntensityLevel.HIGH)
        assertThat(highMass.accelerationMs2).isLessThan(lowMass.accelerationMs2)
    }

    @Test
    fun `aceleracion es fuerza entre masa`() {
        val result = ForceMassEngine.simulate(IntensityLevel.MEDIUM, IntensityLevel.MEDIUM)
        assertThat(result.accelerationMs2).isEqualTo(result.forceN / result.massKg)
    }

    @Test
    fun `aceleracion normalizada esta entre 0 y 1`() {
        val result = ForceMassEngine.simulate(IntensityLevel.MEDIUM, IntensityLevel.MEDIUM)
        val normalized = ForceMassEngine.normalizedAcceleration(result)
        assertThat(normalized).isAtLeast(0f)
        assertThat(normalized).isAtMost(1f)
    }
}
