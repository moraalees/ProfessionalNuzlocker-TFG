package com.example.professionalnuzlocker.data.model

import com.google.firebase.Timestamp

data class CausaMuerte(
    val tipoEntrenador: String = "",
    val ataque: String? = null,
    val idPokemonAsesino: Int = 0,
    val fechaMuerte: Timestamp? = null,
    val combateId: Int = -1
)
