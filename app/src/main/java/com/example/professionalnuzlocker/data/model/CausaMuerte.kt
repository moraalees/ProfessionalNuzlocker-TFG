package com.example.professionalnuzlocker.data.model

import com.google.firebase.Timestamp

/** Información sobre cómo murió un Pokémon: tipo de entrenador responsable, ataque usado, ID del Pokémon asesino y en qué combate ocurrió. */
data class CausaMuerte(
    val tipoEntrenador: String = "",
    val ataque: String? = null,
    val idPokemonAsesino: Int = 0,
    val fechaMuerte: Timestamp? = null,
    val combateId: Int = -1
)
