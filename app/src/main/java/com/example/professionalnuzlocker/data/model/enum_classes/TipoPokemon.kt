package com.example.professionalnuzlocker.data.model.enum_classes

import androidx.compose.ui.graphics.Color

/** Tipos Pokémon del juego, cada uno con su color asociado para mostrarlo en la UI. */
enum class TipoPokemon(val color: Color) {
    NORMAL(Color(0xFFEEEAEA)),
    FUEGO(Color(0xFFEE8130)),
    AGUA(Color(0xFF6390F0)),
    PLANTA(Color(0xFF7AC74C)),
    ELECTRICO(Color(0xFFF7D02C)),
    HIELO(Color(0xFF96D9D6)),
    LUCHA(Color(0xFFC22E28)),
    VENENO(Color(0xFFA33EA1)),
    TIERRA(Color(0xFFE2BF65)),
    VOLADOR(Color(0xFFC1B4FA)),
    PSIQUICO(Color(0xFFF95587)),
    BICHO(Color(0xFFA2B613)),
    ROCA(Color(0xFF8C7F2B)),
    FANTASMA(Color(0xFF735797)),
    DRAGON(Color(0xFF6F35FC)),
    SINIESTRO(Color(0xFF705746)),
    ACERO(Color(0xFFB7B7CE))
}