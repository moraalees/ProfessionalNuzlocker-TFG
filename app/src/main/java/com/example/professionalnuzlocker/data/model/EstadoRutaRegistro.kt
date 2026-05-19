package com.example.professionalnuzlocker.data.model

/** Estado de una ruta durante el flujo de registro: libre sin visitar, Pokémon seleccionado pendiente de confirmar, ya capturado con mote y nivel, o debilitado en el intento. */
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
