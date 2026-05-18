package com.example.professionalnuzlocker.data.model

import com.example.professionalnuzlocker.data.model.enum_classes.TipoPokemon

data class PokemonRival(
    val especie: Pokemon? = null,
    val nivel: Int = 0,
    val movimientos: Map<String, TipoPokemon> = mapOf(),
    val habilidad: String = "",
    val objeto: String? = ""
)