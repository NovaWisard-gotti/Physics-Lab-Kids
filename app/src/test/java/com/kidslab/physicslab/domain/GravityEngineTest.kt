package com.kidslab.physicslab.domain

import com.google.common.truth.Truth.assertThat
import com.kidslab.physicslab.domain.engine.GravityEngine
import com.kidslab.physicslab.domain.model.PlanetMode
import org.junit.Test

class GravityEngineTest {

    @Test
    fun `en caida ideal la masa no afecta el tiempo de caida`() {
        val light = GravityEngine.simulateFall("Liviano", massKg = 0.2, heightM = 5.0, planet = PlanetMode.EARTH)
        val heavy = GravityEngine.simulateFall("Pesado", massKg = 5.0, heightM = 5.0, planet = PlanetMode.EARTH)
        assertThat(light.fallTimeS).isEqualTo(heavy.fallTimeS)
    }

    @Test
    fun `mas altura implica mas tiempo de caida`() {
        val low = GravityEngine.simulateFall("Objeto", 1.0, 2.0, PlanetMode.EARTH)
        val high = GravityEngine.simulateFall("Objeto", 1.0, 8.0, PlanetMode.EARTH)
        assertThat(high.fallTimeS).isGreaterThan(low.fallTimeS)
    }

    @Test
    fun `en la luna se tarda mas en caer que en la tierra`() {
        val earth = GravityEngine.simulateFall("Objeto", 1.0, 5.0, PlanetMode.EARTH)
        val moon = GravityEngine.simulateFall("Objeto", 1.0, 5.0, PlanetMode.MOON)
        assertThat(moon.fallTimeS).isGreaterThan(earth.fallTimeS)
    }

    @Test
    fun `compareTwoObjects devuelve tiempos iguales con distinta masa`() {
        val (a, b) = GravityEngine.compareTwoObjects(6.0, PlanetMode.EARTH, "A" to 0.5, "B" to 4.0)
        assertThat(a.fallTimeS).isEqualTo(b.fallTimeS)
    }
}
