package com.kidslab.physicslab.domain

import com.google.common.truth.Truth.assertThat
import com.kidslab.physicslab.domain.engine.MovementEngine
import com.kidslab.physicslab.domain.engine.SpeedRaceEngine
import org.junit.Test

class MovementEngineTest {

    @Test
    fun `distancia es velocidad por tiempo`() {
        val result = MovementEngine.simulate(initialVelocityMs = 2.0, timeS = 3.0)
        assertThat(result.distanceM).isEqualTo(6.0)
    }

    @Test
    fun `duplicar el tiempo duplica la distancia`() {
        val base = MovementEngine.simulate(4.0, 2.0)
        val doubledTime = MovementEngine.simulate(4.0, 4.0)
        assertThat(doubledTime.distanceM).isEqualTo(base.distanceM * 2)
    }

    @Test
    fun `velocidad cero no recorre distancia`() {
        val result = MovementEngine.simulate(0.0, 10.0)
        assertThat(result.distanceM).isEqualTo(0.0)
    }

    @Test
    fun `velocidad negativa lanza excepcion`() {
        try {
            MovementEngine.simulate(-1.0, 5.0)
            org.junit.Assert.fail("Debió lanzar excepción")
        } catch (e: IllegalArgumentException) {
            // esperado
        }
    }

    @Test
    fun `fractionAt devuelve valores entre 0 y 1`() {
        assertThat(MovementEngine.fractionAt(0.0, 4.0)).isEqualTo(0f)
        assertThat(MovementEngine.fractionAt(2.0, 4.0)).isEqualTo(0.5f)
        assertThat(MovementEngine.fractionAt(10.0, 4.0)).isEqualTo(1f)
    }

    @Test
    fun `carrera ordena de mayor a menor distancia`() {
        val results = SpeedRaceEngine.race(
            listOf("A" to 2.0, "B" to 5.0, "C" to 3.0),
            timeS = 4.0
        )
        assertThat(results.first().label).isEqualTo("B")
        assertThat(results.last().label).isEqualTo("A")
    }
}
