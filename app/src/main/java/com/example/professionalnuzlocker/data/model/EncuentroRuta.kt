package com.example.professionalnuzlocker.data.model

/** Registro del encuentro Nuzlocke en una ruta: si fue visitada, el resultado (capturado/perdido) y qué Pokémon se obtuvo. */
data class EncuentroRuta(
    val rutaId: String = "",
    val resultado: String = "SIN_VISITAR",
    val pokemonCapturadoId: String? = null
)
