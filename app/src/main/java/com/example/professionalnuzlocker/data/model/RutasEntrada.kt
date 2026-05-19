package com.example.professionalnuzlocker.data.model

import com.example.professionalnuzlocker.data.model.enum_classes.Rutas

/** Asocia una ruta del juego con los IDs de los Pokémon que pueden aparecer en ella. */
data class RutasEntrada(
    val ruta: Rutas,
    val pokemonIds: MutableList<Int> = mutableListOf()
)