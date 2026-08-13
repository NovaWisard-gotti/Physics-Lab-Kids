package com.kidslab.physicslab.domain

import com.google.common.truth.Truth.assertThat
import com.kidslab.physicslab.domain.engine.EnergyEngine
import com.kidslab.physicslab.domain.engine.SimpleMachineEngine
import org.junit.Test

class EnergyEngineTest {

    @Test
    fun `arriba de la colina toda la energia es potencial`() {
        val point = EnergyEngine.energyAt(massKg = 2.0, startHeightM = 5.0, heightM = 5.0)
        assertThat(point.potentialFraction).isWithin(0.001f).of(1f)
        assertThat(point.kineticFraction).isWithin(0.001f).of(0f)
    }

    @Test
    fun `abajo de la colina toda la energia es cinetica`() {
        val point = EnergyEngine.energyAt(massKg = 2.0, startHeightM = 5.0, heightM = 0.0)
        assertThat(point.kineticFraction).isWithin(0.001f).of(1f)
        assertThat(point.potentialFraction).isWithin(0.001f).of(0f)
    }

    @Test
    fun `mayor altura inicial produce mayor velocidad final`() {
        val low = EnergyEngine.energyAt(2.0, 2.0, 0.0)
        val high = EnergyEngine.energyAt(2.0, 8.0, 0.0)
        assertThat(high.speedMs).isGreaterThan(low.speedMs)
    }
}

class SimpleMachineEngineTest {

    @Test
    fun `palanca con brazo de esfuerzo mayor necesita menos fuerza`() {
        val shortArm = SimpleMachineEngine.lever(loadWeightN = 20.0, effortArmM = 0.5, loadArmM = 0.5)
        val longArm = SimpleMachineEngine.lever(loadWeightN = 20.0, effortArmM = 2.0, loadArmM = 0.5)
        assertThat(longArm.requiredForceN).isLessThan(shortArm.requiredForceN)
    }

    @Test
    fun `polea con dos cuerdas necesita la mitad de fuerza`() {
        val oneRope = SimpleMachineEngine.pulley(loadWeightN = 20.0, supportingRopes = 1)
        val twoRopes = SimpleMachineEngine.pulley(loadWeightN = 20.0, supportingRopes = 2)
        assertThat(twoRopes.requiredForceN).isEqualTo(oneRope.requiredForceN / 2)
    }

    @Test
    fun `rampa mas larga para la misma altura necesita menos fuerza`() {
        val shortRamp = SimpleMachineEngine.inclinedPlane(loadWeightN = 20.0, rampLengthM = 2.0, heightM = 1.0)
        val longRamp = SimpleMachineEngine.inclinedPlane(loadWeightN = 20.0, rampLengthM = 6.0, heightM = 1.0)
        assertThat(longRamp.requiredForceN).isLessThan(shortRamp.requiredForceN)
    }
}
