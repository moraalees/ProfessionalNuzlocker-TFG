package com.example.professionalnuzlocker.data.model

import com.example.professionalnuzlocker.data.model.enum_classes.EstadoPokemon
import com.example.professionalnuzlocker.data.model.enum_classes.PokemonInicial
import com.example.professionalnuzlocker.data.model.enum_classes.VersionJuego
import org.junit.Assert.*
import org.junit.Test

/**
 * Tests unitarios para [Partida].
 *
 * Verifican los valores por defecto de una partida recién creada (vidas, flags,
 * listas vacías, contadores) y la gestión manual de las listas de equipo, PC y
 * muertos que el ViewModel realiza durante el juego.
 *
 * Son tests JVM puros: no requieren dispositivo ni emulador.
 */
class PartidaTest {

    private fun nuevaPartida() = Partida(
        versionJuego = VersionJuego.NEGRO.nombre,
        nombreJugador = "Ash",
        pokemonInicial = PokemonInicial.SNIVY.nombre
    )

    @Test
    fun `una partida nueva tiene 10 vidas`() {
        assertEquals(10, nuevaPartida().vidas)
    }

    @Test
    fun `finDeLocke es false al inicio`() {
        assertFalse(nuevaPartida().finDeLocke)
    }

    @Test
    fun `consultasIA empieza en cero`() {
        assertEquals(0, nuevaPartida().consultasIA)
    }

    @Test
    fun `las listas de equipo, pc y muertos comienzan vacias`() {
        val partida = nuevaPartida()
        assertTrue(partida.equipo.isEmpty())
        assertTrue(partida.pc.isEmpty())
        assertTrue(partida.muertos.isEmpty())
    }

    @Test
    fun `se puede agregar un pokemon al equipo`() {
        val partida = nuevaPartida()
        val pokemon = PokemonCapturado(id = "p1", especieId = 495)
        partida.equipo.add(pokemon)
        assertEquals(1, partida.equipo.size)
        assertEquals("p1", partida.equipo[0].id)
    }

    @Test
    fun `al mover al PC el equipo queda vacio y el PC tiene el pokemon`() {
        val partida = nuevaPartida()
        val pokemon = PokemonCapturado(id = "p1", especieId = 495)
        partida.equipo.add(pokemon)

        partida.equipo.remove(pokemon)
        pokemon.estado = EstadoPokemon.PC
        partida.pc.add(pokemon)

        assertTrue(partida.equipo.isEmpty())
        assertEquals(1, partida.pc.size)
        assertEquals(EstadoPokemon.PC, partida.pc[0].estado)
    }

    @Test
    fun `al morir un pokemon pasa de equipo a lista de muertos`() {
        val partida = nuevaPartida()
        val pokemon = PokemonCapturado(id = "p1", especieId = 495)
        partida.equipo.add(pokemon)

        partida.equipo.remove(pokemon)
        pokemon.estado = EstadoPokemon.MUERTO
        pokemon.causaMuerte = CausaMuerte(tipoEntrenador = "N", ataque = "Fusion Flare")
        partida.muertos.add(pokemon)

        assertTrue(partida.equipo.isEmpty())
        assertEquals(1, partida.muertos.size)
        assertEquals(EstadoPokemon.MUERTO, partida.muertos[0].estado)
        assertNotNull(partida.muertos[0].causaMuerte)
    }

    @Test
    fun `nombreJugador se conserva correctamente`() {
        val partida = nuevaPartida()
        assertEquals("Ash", partida.nombreJugador)
    }

    @Test
    fun `versionJuego se conserva correctamente`() {
        val partida = nuevaPartida()
        assertEquals(VersionJuego.NEGRO.nombre, partida.versionJuego)
    }
}
