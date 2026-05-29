package com.example.professionalnuzlocker.data.model.enum_classes

import org.junit.Assert.*
import org.junit.Test

/**
 * Tests unitarios para los enums del dominio: [PokemonInicial], [VersionJuego] y [EstadoPokemon].
 *
 * Garantizan que los valores y propiedades de los enums son exactamente los esperados por la
 * lógica de generación de combates ([CombateRepository]) y la pantalla de configuración de
 * partida. Si alguien añade, elimina o renombra un valor, los tests lo detectan de inmediato.
 *
 * Son tests JVM puros: no requieren dispositivo ni emulador.
 */
class EnumsTest {

    @Test
    fun `hay exactamente tres pokemon iniciales`() {
        assertEquals(3, PokemonInicial.entries.size)
    }

    @Test
    fun `los nombres de PokemonInicial son los correctos`() {
        assertEquals("Snivy", PokemonInicial.SNIVY.nombre)
        assertEquals("Tepig", PokemonInicial.TEPIG.nombre)
        assertEquals("Oshawott", PokemonInicial.OSHAWOTT.nombre)
    }

    @Test
    fun `los tres iniciales estan presentes por nombre`() {
        val nombres = PokemonInicial.entries.map { it.nombre }
        assertTrue(nombres.containsAll(listOf("Snivy", "Tepig", "Oshawott")))
    }

    @Test
    fun `hay exactamente dos versiones de juego`() {
        assertEquals(2, VersionJuego.entries.size)
    }

    @Test
    fun `los nombres de VersionJuego son Negro y Blanco`() {
        assertEquals("Negro", VersionJuego.NEGRO.nombre)
        assertEquals("Blanco", VersionJuego.BLANCO.nombre)
    }

    @Test
    fun `Negro y Blanco son versiones distintas`() {
        assertNotEquals(VersionJuego.NEGRO, VersionJuego.BLANCO)
    }

    @Test
    fun `hay exactamente tres estados de pokemon`() {
        assertEquals(3, EstadoPokemon.entries.size)
    }

    @Test
    fun `los tres estados esperados existen`() {
        val estados = EstadoPokemon.entries.map { it.name }
        assertTrue(estados.containsAll(listOf("EQUIPO", "PC", "MUERTO")))
    }

    @Test
    fun `EQUIPO, PC y MUERTO son valores distintos entre si`() {
        assertNotEquals(EstadoPokemon.EQUIPO, EstadoPokemon.PC)
        assertNotEquals(EstadoPokemon.PC, EstadoPokemon.MUERTO)
        assertNotEquals(EstadoPokemon.EQUIPO, EstadoPokemon.MUERTO)
    }
}
