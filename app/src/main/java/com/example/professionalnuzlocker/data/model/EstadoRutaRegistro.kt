package com.example.professionalnuzlocker.data.model

sealed class EstadoRutaRegistro {
    object RutaLibre: EstadoRutaRegistro()

    data class PokemonElegido(
        val pokemon: Pokemon,
        val esInicial: Boolean = false
    ): EstadoRutaRegistro()

    data class PokemonCapturado(
        val pokemon: Pokemon,
        val mote: String,
        val nivel: String
    ): EstadoRutaRegistro()

    data class PokemonDebilitado(val pokemon: Pokemon) : EstadoRutaRegistro()
}
