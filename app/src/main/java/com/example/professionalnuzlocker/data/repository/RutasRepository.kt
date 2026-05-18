package com.example.professionalnuzlocker.data.repository

import com.example.professionalnuzlocker.data.model.Pokemon
import com.example.professionalnuzlocker.data.model.enum_classes.Rutas
import com.example.professionalnuzlocker.data.model.RutasEntrada

object RutasRepository {
    private val listaRutas: MutableList<RutasEntrada> = mutableListOf()

    init {
        generarRutas()
    }

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

    fun getRutas(): List<RutasEntrada> = listaRutas

    fun getPokemonPorRuta(nombreRuta: String): List<Pokemon> {
        val entrada = listaRutas.find { it.ruta.nombreRuta == nombreRuta }

        return entrada?.pokemonIds?.mapNotNull { id ->
            Pokedex.getPokemon().find { it.idPokedex == id }
        } ?: emptyList()
    }

    fun getPokemonPorRuta(ruta: Rutas): List<Pokemon> = getPokemonPorRuta(ruta.nombreRuta)
}