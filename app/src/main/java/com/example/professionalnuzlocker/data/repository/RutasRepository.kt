package com.example.professionalnuzlocker.data.repository

import com.example.professionalnuzlocker.data.model.Pokemon
import com.example.professionalnuzlocker.data.model.enum_classes.Rutas
import com.example.professionalnuzlocker.data.model.RutasEntrada

/**
 * Repositorio singleton que mapea cada ruta del juego con los Pokémon capturables en ella.
 *
 * Se inicializa una sola vez en el bloque `init` cruzando todas las entradas del enum [Rutas]
 * con los datos de captura de la [Pokedex]. Expone [getRutas] para la tabla completa y
 * [getPokemonPorRuta] (sobrecargado por nombre o por enum) para consultar una ruta concreta.
 */
object RutasRepository {
    private val listaRutas: MutableList<RutasEntrada> = mutableListOf()

    init {
        generarRutas()
    }

    /** Recorre todas las rutas y todos los Pokémon de la Pokédex para construir la tabla ruta→Pokémon disponibles. */
    private fun generarRutas() {
        listaRutas.clear()

        Rutas.entries.forEach { rutaEnum ->
            val entrada = RutasEntrada(ruta = rutaEnum)

            Pokedex.getPokemon().forEach { pokemon ->
                if (pokemon.rutasCaptura.isNotEmpty() && pokemon.rutasCaptura.contains(rutaEnum.nombreRuta)) {
                    entrada.pokemonIds.add(pokemon.idPokedex)
                }
            }

            listaRutas.add(entrada)
        }
    }

    /** Devuelve la lista completa de rutas con sus Pokémon disponibles. */
    fun getRutas(): List<RutasEntrada> = listaRutas

    /** Devuelve los Pokémon capturables en la ruta con el nombre indicado, o una lista vacía si la ruta no existe. */
    fun getPokemonPorRuta(nombreRuta: String): List<Pokemon> {
        val entrada = listaRutas.find { it.ruta.nombreRuta == nombreRuta }

        return entrada?.pokemonIds?.mapNotNull { id ->
            Pokedex.getPokemon().find { it.idPokedex == id }
        } ?: emptyList()
    }

    /** Sobrecarga que acepta directamente un enum [Rutas] en lugar del nombre como cadena. */
    fun getPokemonPorRuta(ruta: Rutas): List<Pokemon> = getPokemonPorRuta(ruta.nombreRuta)
}