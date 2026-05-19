package com.example.professionalnuzlocker.data.model.enum_classes

/** Pantallas de la app a las que se puede navegar, con su ruta de navegación como cadena para el NavController. */
enum class RutasNavegacion(val nombre: String) {
    HOME("home"),
    GUIA("guia"),
    FORMULARIO("formulario"),
    REGISTRO_JUEGO("registro_juego"),
    POKEDEX("pokedex"),
    DATOS_JUEGO("datos_juego"),
    CHAT_IA("chat_ia"),
    LOGIN("login"),
    REGISTRO_AUTH("registro_auth"),
    ESTADISTICAS("estadisticas")
}