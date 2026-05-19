package com.example.professionalnuzlocker.data.model.enum_classes

/** Versión del juego elegida por el jugador (Negro o Blanco), que afecta a los Pokémon disponibles en ciertas rutas y al legendario del combate final. */
enum class VersionJuego(val nombre: String) {
    NEGRO("Negro"),
    BLANCO("Blanco")
}