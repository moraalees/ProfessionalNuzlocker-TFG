package com.example.professionalnuzlocker.data.model

import androidx.annotation.DrawableRes
import com.example.professionalnuzlocker.data.model.enum_classes.Rutas
import com.example.professionalnuzlocker.data.model.enum_classes.TipoCombate
import com.example.professionalnuzlocker.data.model.PokemonRival

/** Combate relevante del Nuzlocke (rival, gimnasio, Equipo Plasma o Liga) con el equipo rival ya configurado y la ruta a partir de la cual está disponible. */
data class CombateImportante(
    val id: Int = 0,
    val nombreRival: String = "",
    val lugar: String = "",
    val tipoCombate: TipoCombate = TipoCombate.RIVAL,
    val equipoRival: MutableList<PokemonRival> = mutableListOf(),
    @DrawableRes val imagenRival: Int = 0,
    val despuesDe: Rutas = Rutas.PUEBLO_ARCILLA
)