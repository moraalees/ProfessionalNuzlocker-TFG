package com.example.professionalnuzlocker.data.model

import com.example.professionalnuzlocker.data.model.enum_classes.EstadoPokemon
import org.junit.Assert.*
import org.junit.Test

/**
 * Tests unitarios para [PokemonCapturado].
 *
 * Cubren: valores por defecto al crear un Pokémon, mutabilidad de campos como [PokemonCapturado.mote],
 * [PokemonCapturado.nivel] y [PokemonCapturado.estado], registro de [CausaMuerte], e igualdad
 * estructural entre instancias (comportamiento de data class).
 *
 * Son tests JVM puros: no requieren dispositivo ni emulador.
 */
class PokemonCapturadoTest {

    @Test
    fun `el estado inicial de un pokemon recien creado es EQUIPO`() {
        val pokemon = PokemonCapturado(id = "p1", especieId = 25)
        assertEquals(EstadoPokemon.EQUIPO, pokemon.estado)
    }

    @Test
    fun `el mote es null por defecto`() {
        val pokemon = PokemonCapturado(id = "p1", especieId = 1)
        assertNull(pokemon.mote)
    }

    @Test
    fun `asignar mote actualiza el campo correctamente`() {
        val pokemon = PokemonCapturado(id = "p1", especieId = 1)
        pokemon.mote = "Compañero"
        assertEquals("Compañero", pokemon.mote)
    }

    @Test
    fun `nivel puede incrementarse`() {
        val pokemon = PokemonCapturado(id = "p1", especieId = 1, nivel = 5)
        pokemon.nivel = 30
        assertEquals(30, pokemon.nivel)
    }

    @Test
    fun `cambiar estado a MUERTO y registrar causa de muerte`() {
        val pokemon = PokemonCapturado(id = "p1", especieId = 6)
        assertNull(pokemon.causaMuerte)

        pokemon.estado = EstadoPokemon.MUERTO
        pokemon.causaMuerte = CausaMuerte(tipoEntrenador = "Líder de Gimnasio", ataque = "Surf")

        assertEquals(EstadoPokemon.MUERTO, pokemon.estado)
        assertEquals("Líder de Gimnasio", pokemon.causaMuerte?.tipoEntrenador)
        assertEquals("Surf", pokemon.causaMuerte?.ataque)
    }

    @Test
    fun `dos instancias con los mismos datos son iguales`() {
        val p1 = PokemonCapturado(id = "abc", especieId = 25, nivel = 20)
        val p2 = PokemonCapturado(id = "abc", especieId = 25, nivel = 20)
        assertEquals(p1, p2)
    }

    @Test
    fun `instancias con distinto id no son iguales`() {
        val p1 = PokemonCapturado(id = "aaa", especieId = 25)
        val p2 = PokemonCapturado(id = "bbb", especieId = 25)
        assertNotEquals(p1, p2)
    }
}
