package com.example.professionalnuzlocker.data.model

import com.example.professionalnuzlocker.data.model.enum_classes.Rutas

data class RutasEntrada(
    val ruta: Rutas,
    val pokemonIds: MutableList<Int> = mutableListOf()
)