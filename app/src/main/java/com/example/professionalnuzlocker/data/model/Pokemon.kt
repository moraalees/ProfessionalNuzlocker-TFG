package com.example.professionalnuzlocker.data.model

import androidx.annotation.DrawableRes
import com.example.professionalnuzlocker.data.model.enum_classes.MetodoEvolutivo
import com.example.professionalnuzlocker.data.model.enum_classes.TipoPokemon

data class Pokemon(
    val idPokedex: Int = 0,
    val nombre: String = "",
    val tipo1: TipoPokemon = TipoPokemon.NORMAL,
    val tipo2: TipoPokemon? = null,
    val habilidades: List<String> = emptyList(),
    val lineaEvolutiva: List<Int> = emptyList(),
    val rutasCaptura: List<String> = emptyList(),
    @DrawableRes val imagen: Int = 0,
    val metodoEvolutivo: Map<MetodoEvolutivo, String> = mapOf()
)