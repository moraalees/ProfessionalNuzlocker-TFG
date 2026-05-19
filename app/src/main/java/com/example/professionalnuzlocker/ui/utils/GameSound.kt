package com.example.professionalnuzlocker.ui.utils

/** Identificadores de los efectos de sonido reproducibles mediante [AudioManager.playSound]. */
enum class GameSound {
    /** Clic de botón genérico. */
    CLICK,
    /** Fanfarria de subida de nivel. */
    LEVEL_UP,
    /** Sonido de captura de Pokémon; también reduce el volumen de la música de fondo momentáneamente. */
    CAPTURE
}